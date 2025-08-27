package cn.northpark.model;

public class User {
    private Integer id;

    private String username;

    private String email;

    private String emailFlag;

    private String tailSlug;

    private String password;

    private String headSpanClass;

    private String headSpan;

    private String headPath;

    private String meta;

    private String blogSite;

    private String dateJoined;

    private String lastLogin;

    private String qqOpenid;

    private String qqInfo;

    private String googleId;

    private String googleInfo;

    private String githubId;

    private String githubInfo;

    private String loginType;

    private String avatarUrl;

    private String realName;

    private String location;

    private String company;

    private String bio;

    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getEmailFlag() {
        return emailFlag;
    }

    public void setEmailFlag(String emailFlag) {
        this.emailFlag = emailFlag == null ? null : emailFlag.trim();
    }

    public String getTailSlug() {
        return tailSlug;
    }

    public void setTailSlug(String tailSlug) {
        this.tailSlug = tailSlug == null ? null : tailSlug.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadSpanClass() {
        return headSpanClass;
    }

    public void setHeadSpanClass(String headSpanClass) {
        this.headSpanClass = headSpanClass == null ? null : headSpanClass.trim();
    }

    public String getHeadSpan() {
        return headSpan;
    }

    public void setHeadSpan(String headSpan) {
        this.headSpan = headSpan == null ? null : headSpan.trim();
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath == null ? null : headPath.trim();
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta == null ? null : meta.trim();
    }

    public String getBlogSite() {
        return blogSite;
    }

    public void setBlogSite(String blogSite) {
        this.blogSite = blogSite == null ? null : blogSite.trim();
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined == null ? null : dateJoined.trim();
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin == null ? null : lastLogin.trim();
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid == null ? null : qqOpenid.trim();
    }

    public String getQqInfo() {
        return qqInfo;
    }

    public void setQqInfo(String qqInfo) {
        this.qqInfo = qqInfo == null ? null : qqInfo.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId == null ? null : googleId.trim();
    }

    public String getGoogleInfo() {
        return googleInfo;
    }

    public void setGoogleInfo(String googleInfo) {
        this.googleInfo = googleInfo == null ? null : googleInfo.trim();
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId == null ? null : githubId.trim();
    }

    public String getGithubInfo() {
        return githubInfo;
    }

    public void setGithubInfo(String githubInfo) {
        this.githubInfo = githubInfo == null ? null : githubInfo.trim();
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType == null ? null : loginType.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", emailFlag='" + emailFlag + '\'' +
                ", tailSlug='" + tailSlug + '\'' +
                '}';
    }
}