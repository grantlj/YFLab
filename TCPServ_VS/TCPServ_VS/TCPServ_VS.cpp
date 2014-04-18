
/*
======================NOTES=====================================
======================WSAData===================================

typedef struct WSAData {
WORD                    wVersion;
WORD                    wHighVersion;
char   szDescription[WSADESCRIPTION_LEN+1];
char    szSystemStatus[WSASYS_STATUS_LEN+1];
unsigned short          iMaxSockets;
unsigned short          iMaxUdpDg;
char FAR *              lpVendorInfo;
} WSADATA, FAR * LPWSADATA;

================Param of Socket Structure=======================
af ：套接字要使用的协议地址族，用常量AF_INET表示使用IP地址

type：描述套接字的协议类型,使用 SOCK_STREAM,SOCK_DGRAM,SOCK_RAW

protocol：说明套接字使用的特定协议 IPPROTO_TCP IPPROTO_UDP IPPROTO_RAW
//===============================================================

===============Bind Function=====================================
int bind（
SOCKET s，
const struct sockaddr FAR* name，
int namelen
）；

s：未绑定套接口的句柄  (上一步创建的)
name：与指定协议有关的地址结构指针,在Winsock中使用 sockaddr_in结构 指定IP地址和端口信息
namelen：地址参数(name)的长度,用sizeof（Sadd）
获得


==============Socket_addr Structure========================
Struct sockaddr_in  {short sin_family;
u_short sin_port;
struct in_addr sin_addr;
char  sin_zero[8];//不用,用0填充
}


============= Function===============================
int listen（
SOCKET s，
int backlog，
）；
s：套接口的句柄 (之前已经创建)
Backlog ：等待连接的最大队列长度
（当写为SOMAXCONN下层服务提供者将该套接字设置为最大合理值）
eg： listen(s, 2）
如果服务器端连接数限制在2，
当有3台客户机同时发出连接请求，
第三个请求的客户端将收到“连接试图被拒绝”的错误代码 10061

===============Accpet Function=========================
=======================================================
SOCKET accept（
SOCKET s，
struct sockaddr* addr,
int*addrlen
）；
s：套接字句柄    s
addr：指向一个缓冲区的指针（指向sockaddr_in结构的指针），
获取对方的地址信息（接收某客户机的连接请求时保存该客户端的IP地址、端口信息）
addrlen ：指向地址长度的指针
指定addr所指空间大小，
也用于返回地址实际长度（如果addr或addrlen为NULL，则没有远程地址信息返回

======================Send Function=====================
int send （
SOCKET s，
const char  FAR* buf，
int len,
int flags
）；
s：已连接套接口 句柄   cli (发送对象！！！！！！）
buf：指向一个缓冲区（用来保存要收/发的数据) Sbuf
len：缓冲区长度   sizeof ( Sbuf )
flags：指定调用方式，通常设置为0
无错:返回发送/接收的字节数 isend       有错:返回SOCKET_ERROR

=====================Recv Function=====================
int recv(
SOCKET s，
const char  FAR* buf，
int len,
int flags
);
s：已连接套接口 句柄  cli
buf：指向一个缓冲区（用来保存要收/发的数据）Rbuf
len：缓冲区长度 sizeof ( Rbuf )
flags：指定调用方式，通常设置为0



*/


//#define SERV_ADDR   127.0.0.1

#include "stdafx.h"

#include <stdio.h>
#include <afxinet.h>

#include <afx.h>

#include <atlstr.h>
#include <fstream>

#include <afxwin.h>
#include <UrlMon.h>
#pragma comment(lib, "urlmon.lib")

#include <winsock2.h>
#pragma comment(lib,"ws2_32.lib")
#include <iostream>
using namespace std;
#include <shellapi.h>
#pragma comment(lib,"shell32.lib")


int cmdtoSend[3];
void sendLightState()
{
	char* destUrl = new char[255];
	sprintf(destUrl, "http://grantlj.gicp.net:8080/YFLab/GetData?reqType=lightState&rnd=%d",
		rand() * 1000);

	TCHAR tdestUrl[255];
	MultiByteToWideChar(CP_ACP, 0, destUrl, -1, tdestUrl, 255);

	CString strHtml = "";   //存放网页数据

	HRESULT hr = URLDownloadToFile(0, tdestUrl,
		_T("D:\\tmp.dat"), 0, NULL);
	int t = -1;
	if (hr == S_OK)
	{
		FILE* f=fopen("D:\\tmp.dat", "r");
		char str[255];
		fgets(str, sizeof(str), f);
		fclose(f);
        
		
		t = (str[29] - '0') * 10;

		if (str[30] >= '0' && str[30] <= '9')
			t = t + (str[30] - '0');
	}

	int dig[10];
	int count = 0;
	while (t > 0)
	{
		dig[count] = t % 2;
		t /= 2;
		count++;
	}



	int lightState1 = dig[0];
	int lightState2 = dig[1];

	
	
	cmdtoSend[0] = 1;
	cmdtoSend[1] = !lightState1;
	cmdtoSend[2] = !lightState2;

	cout << cmdtoSend [1]<< cmdtoSend[2]<<endl;

}


void sendData(int junction, int lightState_1, int lightState_2, int temperature, int humidity,int smog,int infrared,int power_h,int power_l,int total)
{
	
	//cout << "in" << endl;
	//CString destUrl;
	//destUrl.Format((wchar_t)("grantlj.gicp.net:8080/YFLab/SetData?reqType=sensorData&junction=%d&light1=%d&light2=%d&temperature=%d&humidity=%d"), junction, lightState_1, lightState_2, temperature, humidity);

	cout << junction <<" "<< lightState_1<<" "<< lightState_2<<" " << temperature<<" " << humidity <<" "<<infrared<<" "<<smog<<" "<<power_h<<" "<<power_l<<" "<<total<<"!!!" << endl;
	if (temperature + humidity != 0)
	{

		char* destUrl = new char[255];

		srand(time(NULL));

		sprintf(destUrl, "http://grantlj.gicp.net:8080/YFLab/SetData?reqType=sensorData&junction=%d&light1=%d&light2=%d&temperature=%d&humidity=%d&infrared=%d&smog=%d&power_hi=%d&power_lo=%d&total=%d&rnd=%d",
			junction,
			lightState_1,
			lightState_2,
			temperature,
			humidity,
			infrared,
			smog,
			power_h,
			power_l,
			total,
			rand() * 1000);

		TCHAR tdestUrl[255];
		MultiByteToWideChar(CP_ACP, 0, destUrl, -1, tdestUrl, 255);

		CInternetSession session;
		CHttpFile *file = NULL;

		CString strHtml = "";   //存放网页数据

		try{
			file = (CHttpFile*)session.OpenURL(tdestUrl);

			//cout << "ok" << endl;
		}
		catch (CInternetException * m_pException){
			//cout << "fail!" << endl;
			cout << "uploading data to server failed." << endl;
			file = NULL;
			m_pException->m_dwError;
			m_pException->Delete();
			//	session.Close();
			//MessageBox("CInternetException");
		}

		

		CString strLine;
		if (file != NULL){
		while (file->ReadString(strLine) != NULL){
		strHtml += strLine;
		}
		//cout << "not null" << endl;

		}

		
		session.Close();
		if (file != NULL)
		{
			file->Close();
			delete file;
			file = NULL;
		}

		sendLightState();
	}




	//ShellExecute(NULL, L"open", L"iexplore.exe", tdestUrl, NULL, SW_SHOW);

}

int main(int argc, char **argv)
{
	CWinApp app((LPCTSTR)argv[0]);
	app.InitApplication();
	AfxWinInit(::GetModuleHandle(NULL), NULL, ::GetCommandLine(), 0);
	
	//===========================================================
	//===========================================================
	//Load Necessary Library;
	printf("Loading Necessary Library...\n");
	//Generate WinSock Version.
	//HI: Vice Version Info.
	//LO: Main Version Info.
	WORD wVersionRequested = MAKEWORD(2, 2);
	//WSADATA is a structure to SAVE INITIAL INFORMATION!!!!
	WSADATA wsaData;
	if (WSAStartup(wVersionRequested, &wsaData) != 0)
	{
		//INITIAL FAILED!!!!
		printf("WSAStartup Failed!!!\n");
		return WSAGetLastError();
	}
	else
		printf("Loaded WSA Library successfully!\n");

	printf("Creating Sockets...\n");

	//===========================================================
	//===========================================================
	//Create Sockets!!!
	SOCKET s;
	//Create s as INTERNET TCP Service!!!!!!

	s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	//AF_INET: Internet Standard;
	//SOCKET_STREAM: TCP;
	//IPPROTO_TCP: TCP standard PROTOL!

	if (s == INVALID_SOCKET)
	{
		printf("Created Socket Failed!!!\n");
		return WSAGetLastError();
	}
	else
		printf("Created Socket successfully!\n");

	//===========================================================
	//===========================================================
	//Bind socket to THIS PC ITSELF!!!
	printf("Binding Socket to local IP...\n");

	struct sockaddr_in Sadd;

	Sadd.sin_family = AF_INET;
	Sadd.sin_port = htons(1400); //if it is 0 then random by system!!!

	//INADDR_ANY: means IGNORE PC'S REAL IP!!!!!ANY IS OK!!!!!
	//Bind IP INFO(Sadd) to Socket(s)!!!!!!!!!!!!!!!!!!!!!!!!!!

	Sadd.sin_addr.S_un.S_addr = htonl(INADDR_ANY);
	// Sadd.sin_addr.S_un.S_addr=inet_addr("127.0.0.1");
	if (bind(s, (sockaddr*)&Sadd, sizeof(Sadd)) == SOCKET_ERROR)
	{
		printf("Bind Socket to IP Failed!!!\n");
		return WSAGetLastError();
	}
	else
		printf("Bind Socket to IP successfully!\n");

	//==================================================
	//==================================================
	//Start Listening....
	printf("Strating Listening...\n");
	if (listen(s, 100) == SOCKET_ERROR)
	{
		printf("Start listening failed!!!\n");
		return WSAGetLastError();
	}
	else
		printf("Start listening successfully!\n");



	//=================================================
	//=================================================
	//Start Accept Clients Request!
	struct sockaddr_in clientadd;
	int addLen = sizeof(clientadd);

	SOCKET cli;
	char Rbuf[10];
	memset(Rbuf, 0, sizeof(Rbuf));
	int nRecv;
	cli = accept(s, (sockaddr*)&clientadd, &addLen);
	while (true)
	{
		// printf("in!\n");
		//By Exp: 阻塞式！！！！！！！

		/*
		} if (cli==INVALID_SOCKET)
		{
		printf("Accepted Client failed!\n");
		continue;
		}
		else
		{*/

		cout << "Receive a new message from IP:" << inet_ntoa(clientadd.sin_addr) << " & Port is:" <<
			ntohs(clientadd.sin_port) << endl;

		nRecv = recv(cli, Rbuf, sizeof(Rbuf), 0);

		//cout << Rbuf << "!!!" << endl;

		if (nRecv == 0) //客户端已经关闭连接
			printf("Client has closed the connection\n");

		if (nRecv == SOCKET_ERROR)
		{
			//printf("recv failed.\n");
			//  printf("Get message from client::::%s",Rbuf);
			//continue;
		}
		else
		{
			int junction = Rbuf[0];
			int lightState_1 = Rbuf[1];
			int lightState_2 = Rbuf[2];
			int temperature = Rbuf[3];
			int humidity = Rbuf[4];
			int smog = Rbuf[5];
			int infrared = Rbuf[6];
			int power_h = Rbuf[7];
			int power_l = Rbuf[8];
			int total = Rbuf[9];

			//cout << junction << "," << lightState_1 << "," << temperature << "," << humidity << endl;
			if (junction==1)
			  sendData(junction, !lightState_1,!lightState_2, temperature, humidity,smog,infrared,power_h,power_l,total);
		}

		
     	send(cli,(char*) cmdtoSend,sizeof(cmdtoSend),0);

		/*
		if (isend==SOCKET_ERROR)
		{
		printf("Send Message to Client failed...\n");
		printf("Error Code:%d\n",WSAGetLastError());
		continue;
		}
		else
		printf("Message [%s] sended successfully.\n",Sbuf);

		SleepEx(1000,true);
		*/

		SleepEx(1000, true);

	}
	closesocket(s);
	WSACleanup();
	return 0;

}
