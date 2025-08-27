# GitHub OAuth 申请指引

## 1. 创建GitHub OAuth App

1. 登录GitHub账号
2. 点击右上角头像 -> Settings
3. 在左侧菜单选择"Developer settings"
4. 选择"OAuth Apps"
5. 点击"New OAuth App"

## 2. 填写应用信息

- **Application name**: NorthPark
- **Homepage URL**: https://yourdomain.com 或 http://localhost:8080
- **Application description**: NorthPark社区网站
- **Authorization callback URL**: 
  - 开发环境：http://localhost:8080/auth/github/callback
  - 生产环境：https://yourdomain.com/auth/github/callback

## 3. 获取客户端信息

创建完成后，你将获得：
- **Client ID**：类似 `Iv1.a1b2c3d4e5f6g7h8`
- **Client Secret**：点击"Generate a new client secret"生成

## 4. 配置应用

将获得的信息配置到application.yml中：

```yaml
oauth:
  github:
    client-id: 你的Client ID
    client-secret: 你的Client Secret
    redirect-uri: http://localhost:8080/auth/github/callback
    scope: user:email
```

## 5. GitHub API权限说明

- `user:email`：获取用户邮箱信息
- `read:user`：读取用户基本信息（用户名、头像等）

## 注意事项

1. Client Secret只显示一次，请妥善保存
2. 回调URL必须完全匹配
3. 生产环境建议使用HTTPS
4. 可以在OAuth App设置中查看和管理授权用户
5. 如果需要获取私有邮箱，用户的GitHub设置中邮箱必须设为公开

## 测试OAuth流程

1. 用户点击"GitHub登录"
2. 跳转到GitHub授权页面
3. 用户确认授权
4. GitHub重定向到你的回调URL并带上授权码
5. 你的应用用授权码换取access_token
6. 使用access_token调用GitHub API获取用户信息