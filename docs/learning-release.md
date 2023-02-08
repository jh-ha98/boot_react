# 배포
서버 배포 관련하여 정리한 학습내용입니다.
- [메인](../README.md)

## 목차
- [Proxy Server](#proxy-server)
- [Nginx](#nginx)
- [SSL](#ssl)

## Proxy Server
- 클라이언트가 자신을 통해서 다른 네트워크 서비스에 간접적으로 접속할 수 있게 해 주는 컴퓨터 시스템이나 응용 프로그램을 가리킨다. 
- 서버와 클라이언트 사이에 중계기로서 대리로 통신을 수행하는 것을 가리켜 ‘프록시’, 그 중계 기능을 하는 것을 프록시 서버라고 부른다.

## Nginx
- Nginx를 통해 Front-End 서버와 API 서버를 하나의 서버처럼 동작하도록 구성합니다. 이를 프록시 서버(proxy server)라고 합니다.
- SSL 설정을 하고 http 요청을 https 요청으로 리다이렉트 시킵니다.
- 설치방법 ([참고 문서](https://nginx.org/en/linux_packages.html#Ubuntu))
    ```bash
    # Install the prerequisites:
    sudo apt install curl gnupg2 ca-certificates lsb-release ubuntu-keyring

    # Import an official nginx signing key so apt could verify the packages authenticity. Fetch the key:
    curl https://nginx.org/keys/nginx_signing.key | gpg --dearmor \
        | sudo tee /usr/share/keyrings/nginx-archive-keyring.gpg >/dev/null

    # Verify that the downloaded file contains the proper key:
    gpg --dry-run --quiet --no-keyring --import --import-options import-show /usr/share/keyrings/nginx-archive-keyring.gpg
    # The output should contain the full fingerprint 573BFD6B3D8FBC641079A6ABABF5BD827BD9BF62 as follows:
    # pub   rsa2048 2011-08-19 [SC] [expires: 2024-06-14]
    #       573BFD6B3D8FBC641079A6ABABF5BD827BD9BF62
    # uid                      nginx signing key <signing-key@nginx.com>
    # If the fingerprint is different, remove the file.

    # To set up the apt repository for stable nginx packages, run the following command:
    echo "deb [signed-by=/usr/share/keyrings/nginx-archive-keyring.gpg] \
    http://nginx.org/packages/ubuntu `lsb_release -cs` nginx" \
        | sudo tee /etc/apt/sources.list.d/nginx.list

    # Set up repository pinning to prefer our packages over distribution-provided ones:
    echo -e "Package: *\nPin: origin nginx.org\nPin: release o=nginx\nPin-Priority: 900\n" \
        | sudo tee /etc/apt/preferences.d/99nginx

    # To install nginx, run the following commands:
    sudo apt update
    sudo apt install nginx
    ```
- 명령어
    ```bash
    # 실행 명령어
    # 설치시 자동시작 함.
    nginx
    # stop — fast shutdown
    # quit — graceful shutdown
    # reload — reloading the configuration file
    # reopen — reopening the log files
    nginx -s reload
    ```
- 초기 설정
    - `/etc/nginx/nginx.conf` 파일의 http 그룹 안에 다음의 server 그릅 코드를 작성합니다.  
    - location 그룹은 각각의 서버를 내가 원하는 url과 맵핑합니다.  
    - 수정후 `nginx -s reload` 명령어를 실행해 수정한 파일의 내용이 반영됩니다.
    ```
    http {
        server {
            server_name my.domain.com;
            location / {
                proxy_pass: http://localhost:3000;
            }
            location /api {
                proxy_pass: http://localhost:8080;
            }
        }
    }
    ```
## SSL
- Let's Encrypt를 사용합니다.  
- Let’s Encrypt는 공공의 이익을 위해 실행되는 무료, 자동화된 개방형 CA입니다.  
- Let's Encrypt는 공신력있는 비영리 단체라고합니다. 믿고 씁시다.  
- Let's Encrypt의 공식문서에 따르면 `Certbot`을 사용할것을 권장합니다.  
- 적용방법 ([참고 문서](https://letsencrypt.org/ko/getting-started/))
    ```bash
    # Ensure that your version of snapd is up to date
    sudo snap install core; sudo snap refresh core
    # Install Certbot
    sudo snap install --classic certbot
    # Prepare the Certbot command
    sudo ln -s /snap/bin/certbot /usr/bin/certbot
    # 설치환경(Nginx)에 맞는 인증서 설정을 알아서 합니다
    sudo certbot --nginx
    # Test automatic renewal
    sudo certbot renew --dry-run
    ```
- 자동갱신
    - Certbot을 설치하면 자동으로 자동갱신 설정이 추가 되어있을 것입니다.  
    - 다음의 경로 또는 명령어를 통해 설정이 되어있는지 확인할 수 있습니다.  
        - `/etc/crontab/`
        - `/etc/cron.*/*`
        - `systemctl list-timers`
- snap 설치 방법
    - Certbot을 설치할때 snap을 이용하길 권장합니다.  
    - snap은 기본적으로 설치되어있을 경우가 많으나 없을시 다음처럼 설치합니다. ([참고문서](https://snapcraft.io/docs/installing-snapd))
    ```bash
    sudo apt update
    sudo apt install snapd
    ```
