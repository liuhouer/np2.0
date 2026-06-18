package cn.northpark.vo;

import lombok.Data;

/**
 * 网站统计信息VO
 * @author bruce
 */
@Data
public class SiteStatisticsVO {
    private Long totalUsers;           // 总用户数
    private Long onlineUsers;          // 在线用户数
    private Long totalMovies;          // 电影总数
    private Long totalSofts;           // 软件总数
    private Long totalKnowledge;       // 知识总数
    private Long totalNotes;           // 树洞笔记总数
    private Long totalLyrics;          // 最爱总数
    private Long totalComments;        // 评论总数
    private Long todayNewUsers;        // 今日新用户
    private Long todayNewMovies;       // 今日新电影
    private Long todayNewComments;     // 今日新评论
}