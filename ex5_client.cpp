#include <iostream>
#include <cstring>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

class Client {
public:
    static int connection;

    static void main() {
        try {
            int v[9];
            int n = 0;
            struct sockaddr_in server_addr;
            inet_pton(AF_INET, "127.0.0.1", &server_addr.sin_addr);
            server_addr.sin_family = AF_INET;
            server_addr.sin_port = htons(8011);

            std::cout << "Connecting to server..." << std::endl;
            connection = socket(AF_INET, SOCK_STREAM, 0);
            if (connect(connection, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
                std::cerr << "Connection failed" << std::endl;
                return;
            }

            int p;
            recv(connection, &p, sizeof(p), 0);
            std::cout << "No of frame is: " << p << std::endl;

            for (int i = 0; i < p; i++) {
                recv(connection, &v[i], sizeof(v[i]), 0);
                std::cout << v[i] << std::endl;
            }
            v[5] = -1;
            for (int i = 0; i < p; i++) {
                std::cout << "Received frame is: " << v[i] << std::endl;
            }
            for (int i = 0; i < p; i++) {
                if (v[i] == -1) {
                    std::cout << "Request to retransmit packet no " << (i + 1) << " again!!" << std::endl;
                    n = i;
                    send(connection, &n, sizeof(n), 0);
                }
            }

            std::cout << std::endl;
            recv(connection, &v[n], sizeof(v[n]), 0);
            std::cout << "Received frame is: " << v[n] << std::endl;

            std::cout << "quiting" << std::endl;
            close(connection);
        } catch (const std::exception& e) {
            std::cerr << e.what() << std::endl;
        }
    }
};

int Client::connection;

int main() {
    Client::main();
    return 0;
}
