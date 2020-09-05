#include <iostream>
#include <unistd.h>
#include "discovery.h"

#define DEFAULT_PORT 18084
#define DEFAULT_INTERVAL 500  // in msec

using namespace std;

int port = DEFAULT_PORT;
int interval = DEFAULT_INTERVAL;
char *serviceId = NULL;

const char optString[] = "s:p:i:";

void displayUsage(void) {
  cout << "Usage: discovery [OPTION...]" << endl;
  cout << "    -s serviceId                 service ID" << endl;
  cout << "    -p port                      port number" << endl;
  cout << "    -i interval                  interval" << endl;
}

void argparse(int argc, char *argv[]) {

  int opt;
  opterr = 0;

  while ( (opt = getopt(argc, argv, optString)) != -1) {
    switch(opt) {
      case 's':
        serviceId = optarg;
        break;
      case 'p':
        port = atoi(optarg);
        break;
      case 'i':
        interval = atoi(optarg);
        break;
      default:
        displayUsage();
        exit(-1);
        break;
    }
  }
}

int main(int argc, char *argv[]) {

  argparse(argc, argv);
  if (serviceId == NULL) {
    cerr << "-s option (service ID) is mandatory!" << endl;
    exit(-1);
  }

  advertise(port, interval, serviceId);

}

