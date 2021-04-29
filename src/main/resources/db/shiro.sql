/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 27/04/2021 10:19:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限绑定url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('3th0660oja173z0280yvlo43489531at', 'user:find:*', NULL);
INSERT INTO `sys_permission` VALUES ('48vkt326sd65ua53pc88m6l4u6wh32mz', 'user:*:*', NULL);
INSERT INTO `sys_permission` VALUES ('788ru3j12r5c582f0za0bb934x80a1vu', 'order:*:*', NULL);
INSERT INTO `sys_permission` VALUES ('8csfo138y6eq146y5m44hezn286r681c', 'product:*:01', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色解释',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('4dca29b4594d39d1d11bbfb52492ca1e', 'merchant', '商户');
INSERT INTO `sys_role` VALUES ('adbaec1895b1bf408d028ce90ccb45e5', 'admin', '管理员');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户权限表id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('3h2upl9t030w5a0n299mm330rrp9t9k1', 'adbaec1895b1bf408d028ce90ccb45e5', '48vkt326sd65ua53pc88m6l4u6wh32mz');
INSERT INTO `sys_role_permission` VALUES ('4083bbns61lb8455qz9f33uk9vu548wb', 'adbaec1895b1bf408d028ce90ccb45e5', '788ru3j12r5c582f0za0bb934x80a1vu');
INSERT INTO `sys_role_permission` VALUES ('833g8s9fu8r1i30596za20oqyw3wf62w', '4dca29b4594d39d1d11bbfb52492ca1e', '8csfo138y6eq146y5m44hezn286r681c');
INSERT INTO `sys_role_permission` VALUES ('g16hq86638hl3ra46tf1881c1o9p1249', '4dca29b4594d39d1d11bbfb52492ca1e', '3th0660oja173z0280yvlo43489531at');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('3a38aacec43f5df6cd4efaa2e50e2037', 'jk', '527958d6e0bbfe34b0864b7bc4dcd996', 'o122ylg4');
INSERT INTO `sys_user` VALUES ('a17099678aa2653429384705724c28d0', 'xiaomin', '9c0df3ebbce53f8c6d67ad561af1a57d', 'f201rg5o');
INSERT INTO `sys_user` VALUES ('b84c26a8bec75fbdee715cbd6e65832f', 'admin', 'd563709589fe30979668c26f1fe08027', '4rtauifk');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户角色表id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('ccd6dca91fcfa20e1626c4cf87bbd7b9', 'a17099678aa2653429384705724c28d0', '4dca29b4594d39d1d11bbfb52492ca1e');
INSERT INTO `sys_user_role` VALUES ('e97ac3556759955630a8307e7f00e900', 'b84c26a8bec75fbdee715cbd6e65832f', 'adbaec1895b1bf408d028ce90ccb45e5');
INSERT INTO `sys_user_role` VALUES ('eb74b20386ce484c0605d6b6967f22d3', 'b84c26a8bec75fbdee715cbd6e65832f', '4dca29b4594d39d1d11bbfb52492ca1e');

SET FOREIGN_KEY_CHECKS = 1;
