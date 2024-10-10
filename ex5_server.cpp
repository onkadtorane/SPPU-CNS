#include <iostream>
#include <vector>
#include <stdexcept>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>

class Server {
public:
    static int main() {
        int server_fd, client_fd;
        struct sockaddr_in address;
        int opt = 1;
        int addrlen = sizeof(address);
        std::vector<int> a = {30, 40, 50, 60, 70, 80, 90, 100, 110};

        try {
            server_fd = socket(AF_INET, SOCK_STREAM, 0);
            if (server_fd == 0) {
                throw std::runtime_error("Socket failed");
            }

            if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))) {
                throw std::runtime_error("Set socket options failed");
            }

            address.sin_family = AF_INET;
            address.sin_addr.s_addr = INADDR_ANY;
            address.sin_port = htons(8011);

            if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
                throw std::runtime_error("Bind failed");
            }

            if (listen(server_fd, 3) < 0) {
                throw std::runtime_error("Listen failed");
            }

            std::cout << "waiting for connection" << std::endl;
            client_fd = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen);
            if (client_fd < 0) {
                throw std::runtime_error("Accept failed");
            }

            std::cout << "The number of packets sent is: " << a.size() << std::endl;
            int y = a.size();
            send(client_fd, &y, sizeof(y), 0);

            for (int i = 0; i < a.size(); i++) {
                send(client_fd, &a[i], sizeof(a[i]), 0);
            }

            int k;
            recv(client_fd, &k, sizeof(k), 0);
            send(client_fd, &a[k], sizeof(a[k]), 0);

        } catch (const std::exception &e) {
            std::cerr << e.what() << std::endl;
        } finally {
            close(client_fd);
            close(server_fd);
        }

        return 0;
    }
};
