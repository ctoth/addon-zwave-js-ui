server {
    listen 8099 default_server;

    proxy_http_version 1.1;
    proxy_set_header Host $http_host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_set_header X-External-Path {{ .entry }};
    proxy_read_timeout 86400;

    location / {
        allow   127.0.0.1;
        allow   172.30.32.2;
        deny    all;

        proxy_pass http://backend;
    }

    location /socket.io {
        allow   127.0.0.1;
        allow   172.30.32.2;
        deny    all;

        proxy_pass http://backend/socket.io;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
