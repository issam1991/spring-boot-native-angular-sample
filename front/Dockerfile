# Build stage
FROM node:20-alpine AS build

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm ci

# Build application
COPY . .
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy Angular build and nginx config
COPY --from=build /app/dist/user-management/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

# Ensure index.html exists (Angular generates index.csr.html)
RUN if [ -f /usr/share/nginx/html/index.csr.html ]; then \
    cp /usr/share/nginx/html/index.csr.html /usr/share/nginx/html/index.html; \
    fi

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
