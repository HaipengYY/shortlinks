package com.nageoffer.shortlink.admin.test;

public class test {

    public final static String Sql = "CREATE TABLE `t_user_%d` (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
            "  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0/未删除: 1',\n" +
            "  `update_time` datetime DEFAULT NULL,\n" +
            "  `create_time` datetime DEFAULT NULL,\n" +
            "  `deletion_time` bigint DEFAULT NULL,\n" +
            "  `mail` varchar(51) DEFAULT NULL,\n" +
            "  `phone` varchar(12) DEFAULT NULL,\n" +
            "  `real_name` varchar(25) DEFAULT NULL,\n" +
            "  `password` varchar(51) DEFAULT NULL,\n" +
            "  `username` varchar(25) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1805159900129583106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    public static void main(String[] args) {
        for(int i = 0; i <= 15; i++){
            System.out.printf(Sql + "%n", i);
        }
    }
}
