#!/bin/bash
# 部署 DayTracker PWA 到 Cloudflare Pages

# 创建目录
mkdir -p /tmp/daytracker-deploy
cp ~/workspace/DayTracker/pwa/* /tmp/daytracker-deploy/

# 创建简单的图标（使用 emoji）
cat > /tmp/daytracker-deploy/icon-192x192.html << 'EOF'
<!DOCTYPE html>
<html>
<head>
<style>
body { margin: 0; display: flex; justify-content: center; align-items: center; height: 100vh; background: #6200EE; }
.icon { font-size: 100px; }
</style>
</head>
<body>
<div class="icon">📍</div>
</body>
</html>
EOF

echo "PWA 文件已准备好"
echo "位置: /tmp/daytracker-deploy/"
echo ""
echo "你可以:"
echo "1. 用手机浏览器直接打开 index.html"
echo "2. 添加到主屏幕（像 App 一样使用）"
echo "3. 或者部署到 Cloudflare Pages"
