/*
SQLyog Ultimate v8.55 
MySQL - 5.7.11-log : Database - library
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `library`;

/*Table structure for table `book_issue` */

DROP TABLE IF EXISTS `book_issue`;

CREATE TABLE `book_issue` (
  `b_issue_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `approve_by` int(11) DEFAULT NULL,
  `date_of_issue` varchar(50) DEFAULT NULL,
  `request_date` varchar(50) DEFAULT NULL,
  `is_return` varchar(2) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`b_issue_id`),
  KEY `FK_book_issue` (`book_id`),
  KEY `FK_book_issue-1` (`user_id`),
  CONSTRAINT `FK_book_issue` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`),
  CONSTRAINT `FK_book_issue-1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `book_issue` */

insert  into `book_issue`(`b_issue_id`,`user_id`,`book_id`,`approve_by`,`date_of_issue`,`request_date`,`is_return`,`status`) values (16,4,3,2,'NULL','2019-03-20','0','1'),(17,4,6,2,'NULL','2019-03-20','0','2'),(28,3,3,2,'NULL','2019-03-27','0','1');

/*Table structure for table `books` */

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_title` varchar(50) DEFAULT NULL,
  `book_author` varchar(50) DEFAULT NULL,
  `book_publisher` varchar(50) DEFAULT NULL,
  `available_copies` int(11) DEFAULT NULL,
  `total_copies` int(11) DEFAULT NULL,
  `book_link` text,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `books` */

insert  into `books`(`book_id`,`book_title`,`book_author`,`book_publisher`,`available_copies`,`total_copies`,`book_link`,`status`) values (3,'English Language','Tom','Jahangir',95,100,'','1'),(6,'English Language','Tom','Jahangir',1,1,'Http:google.com','1');

/*Table structure for table `login_credentials` */

DROP TABLE IF EXISTS `login_credentials`;

CREATE TABLE `login_credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` text,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `FK99A9B26F69FE27A` (`id`),
  CONSTRAINT `FK_login_credentials` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `login_credentials` */

insert  into `login_credentials`(`id`,`user_id`,`email`,`password`,`status`) values (1,2,'admin@admin.com','admin','1'),(2,1,'memonzeeeshanali.786@gmail.com','admin','1'),(3,3,'user@user.com','user','1'),(4,4,'razadahri@sapphire.co','admin',NULL),(5,5,'user1234@user.com','user',NULL);

/*Table structure for table `notifications` */

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `n_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  `message` text,
  `n_date_time` varchar(50) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  `sdelete` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`n_id`),
  KEY `FK4BD694E8EDDAC36E` (`user_id`),
  CONSTRAINT `FK4BD694E8EDDAC36E` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `notifications` */

insert  into `notifications`(`n_id`,`user_id`,`agent_id`,`message`,`n_date_time`,`status`,`sdelete`) values (1,3,3,'Send Request For Book Issue','2019-03-27','0','1');

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`role_id`,`role`,`status`) values (1,'Admin','1'),(2,'Agent','1'),(3,'Subscriber','1');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_users` (`role_id`),
  CONSTRAINT `FK_users` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`user_id`,`role_id`,`first_name`,`last_name`,`status`) values (1,3,'Zeeshan','Memon','1'),(2,1,'Admin','Memon','1'),(3,3,'User','Memon','1'),(4,3,'Raza','Dahri','2'),(5,3,'Test','User 1','2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
