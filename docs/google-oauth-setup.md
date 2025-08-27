# Google OAuth 2.0 申请指引

## 1. 创建Google Cloud项目

1. 访问 [Google Cloud Console](https://console.cloud.google.com/)
2. 登录你的Google账号
3. 点击"选择项目" -> "新建项目"
4. 输入项目名称（如：northpark-oauth）
5. 点击"创建"

## 2. 启用Google+ API

1. 在左侧菜单选择"API和服务" -> "库"
2. 搜索"Google+ API"或"People API"
3. 点击进入并启用API

## 3. 配置OAuth同意屏幕（重要步骤）

1. 在左侧菜单选择"API和服务" -> "OAuth同意屏幕"
2. 选择"外部"用户类型，点击"创建"
3. 填写OAuth同意屏幕信息：
   - **应用名称**：NorthPark
   - **用户支持电子邮件**：你的邮箱
   - **应用徽标**：可选
   - **应用首页链接**：https://yourdomain.com 或 http://localhost:8082
   - **应用隐私政策链接**：可选
   - **应用服务条款链接**：可选
   - **已获授权的网域**：yourdomain.com（生产环境域名）
   - **开发者联系信息**：你的邮箱

4. 点击"保存并继续"

5. **配置范围（Scopes）**：
   - 点击"添加或移除范围"
   - 选择以下范围：
     - `../auth/userinfo.email` - 查看您的电子邮件地址
     - `../auth/userinfo.profile` - 查看您的个人信息
     - `openid` - 将您与您的个人信息相关联
   - 点击"更新"
   - 点击"保存并继续"

6. **测试用户**（开发阶段）：
   - 添加测试用户邮箱（只有这些用户能在开发阶段使用OAuth）
   - 点击"保存并继续"

## 4. 创建OAuth 2.0凭据

1. 在左侧菜单选择"API和服务" -> "凭据"
2. 点击"创建凭据" -> "OAuth 2.0客户端ID"
3. 创建OAuth 2.0客户端ID：
   - **应用类型**：Web应用
   - **名称**：NorthPark Web Client
   - **授权的重定向URI**：
     - http://localhost:8082/auth/google/callback
     - https://yourdomain.com/auth/google/callback

## 5. 获取客户端信息

创建完成后，你将获得：
- **客户端ID**：类似 `123456789-abcdefg.apps.googleusercontent.com`
- **客户端密钥**：类似 `GOCSPX-abcdefghijklmnop`

## 6. 配置应用

将获得的信息配置到application.yml中：

```yaml
oauth:
  google:
    client-id: 你的客户端ID
    client-secret: 你的客户端密钥
    redirect-uri: http://localhost:8080/auth/google/callback
    scope: openid,email,profile
```

## 故障排除

### 错误：invalid_scope
如果遇到 `Some requested scopes were invalid. {invalid=[openid,email,profile]}` 错误：

1. **检查OAuth同意屏幕配置**：
   - 确保在OAuth同意屏幕中正确添加了所需的范围
   - 范围应该是：`../auth/userinfo.email`、`../auth/userinfo.profile`、`openid`

2. **检查应用状态**：
   - 确保OAuth同意屏幕状态不是"需要验证"
   - 开发阶段可以保持"测试"状态

3. **重新保存配置**：
   - 重新保存OAuth同意屏幕配置
   - 等待几分钟让配置生效

### 错误：redirect_uri_mismatch
- 检查重定向URI是否完全匹配
- 注意端口号：开发环境使用8082，不是8080

## 注意事项

1. 开发环境可以使用localhost，生产环境必须使用HTTPS
2. 重定向URI必须完全匹配，包括协议、域名、端口、路径
3. 客户端密钥要妥善保管，不要提交到代码仓库
4. 定期检查和更新OAuth同意屏幕信息
5. 开发阶段应用状态保持"测试"，只有测试用户能使用