FROM nginx:1.13.12-alpine
ADD target/classes/nginx.conf /etc/nginx/nginx.conf
ADD target/classes/cert.pem /etc/nginx/cert.pem
ADD target/classes/key.pem /etc/nginx/key.pem
ADD target/classes/global.pass /etc/nginx/global.pass