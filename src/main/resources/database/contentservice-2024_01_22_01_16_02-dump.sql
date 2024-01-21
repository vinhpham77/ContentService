-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: contentservice
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `post_tags`
--

DROP TABLE IF EXISTS `post_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tags` (
  `post_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `FKm6cfovkyqvu5rlm6ahdx3eavj` (`tag_id`),
  CONSTRAINT `FKkifam22p4s1nm3bkmp1igcn5w` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKm6cfovkyqvu5rlm6ahdx3eavj` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tags`
--

LOCK TABLES `post_tags` WRITE;
/*!40000 ALTER TABLE `post_tags` DISABLE KEYS */;
INSERT INTO `post_tags` VALUES (5,1),(6,1),(7,1),(8,1),(9,1),(3,5),(8,6),(4,7),(1,11),(2,11),(5,15),(7,15);
/*!40000 ALTER TABLE `post_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment_count` int NOT NULL,
  `content` longtext NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `is_private` bit(1) NOT NULL,
  `score` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,0,'# Java Relection\n Java Reflecion cho phép bạn đánh giá, sửa đổi cấu trúc và hành vi của một đối tượng tại thời gian chạy (runtime) của chương trình. Đồng thời nó cho phép bạn truy cập vào các thành viên private (private member) tại mọi nơi trong ứng dụng, điều này không được phép với cách tiếp cận truyền thống.\n # Lấy ra Thuộc tính (Field)\n Quay trở lại ví dụ trên, Chúng ta sẽ lấy ra toàn bộ thuộc tính của Girl. Tìm xem cái nào tên name và bổ sung giá trị mới cho nó.\n # Lấy ra Hàm (Method)\n Vấn đề đặt ra, giống với field. Chúng ta cũng sẽ có nhu cầu duyệt tìm một method nào đó và sử dụng nó.\n # Lấy ra Constructor\n Lấy ra hàm khởi tạo của một class. Từ đó cho phép chúng ta cách tạo ra đối tượng từ theo một cách khác, thay vì new Class() như bình thường.\n # Lấy ra Annotation trên Field, Method, Class\n Đúng vậy, đây cũng chính là một trong những phần quan trọng bậc nhất của Java Reflection. Cho phép chúng ta kiểm tra Class hiện tại đang được chú thích bởi những Annotation nào.\n Bài viết tới đây kết thúc, bạn đã có thể sử dụng Java Reflection xoành xoạch rồi đó, chúc bạn học tập tốt ahoho!','luongnguyen',_binary '\0',0,'「Java」Hướng dẫn Java Reflection','2024-01-21 17:53:42.794000'),(2,0,'Gói này cung cấp các lớp và interface như bên dưới cho các biểu thức chính quy. Các lớp Matcher và Pattern được sử dụng rộng rãi trong các biểu thức chính quy của java.\n \n - Interface MatchResult\n - Lớp Matcher\n - Lớp Pattern\n - Lớp PatternSyntaxException\n - Lớp Matcher\n \n Lớp Matcher thực thi interface MatchResult. Nó được sử dụng để thực hiện các hoạt động so khớp trên một chuỗi ký tự.\n \n Lớp Pattern\n Một số ký tự đặc biệt trong Java Regex\n \n \\.[{(*+?^$|\n \n Những ký tự liệt kê ở trên là các ký tự đặc biệt. Trong Java Regex bạn muốn nó hiểu các ký tự đó theo cách thông thường bạn cần thêm dấu \\ ở phía trước.\n \n Chẳng hạn ký tự chấm . java regex đang hiểu là một ký tự bất kỳ, nếu bạn muốn nó hiểu là một ký tự chấm thông thường, cần phải có dấu \\ phía trước.\n \n Cảm ơn các bạn đã đọc đến dòng này 😄!!!\n \n Đây là phiên bản biên dịch của một biểu thức chính quy. Nó được sử dụng để xác định một khuôn mẫu cho các biểu thức chính quy.\n \n PatternSyntaxException\n \n PatternSyntaxException sẽ xảy ra khi mà có lỗi cú pháp trong mẫu Regular Expression.','luongnguyen',_binary '\0',0,'Java Regex','2024-01-21 17:54:13.843000'),(3,0,'## 1. SQL Injection là gì?\n SQL Injection là một kỹ thuật cho phép những kẻ tấn công lợi dụng lỗ hổng của việc kiểm tra dữ liệu đầu vào trong các ứng dụng web và các thông báo lỗi của hệ quản trị cơ sở dữ liệu trả về để inject (tiêm vào) và thi hành các câu lệnh SQL bất hợp pháp. SQL injection có thể cho phép những kẻ tấn công thực hiện các thao tác, delete, insert, update, ... trên cơ sở dữ liệu của ứng dụng, thậm chí là server mà ứng dụng đó đang chạy. SQL Injection thường được biết đến như là một vật trung gian tấn công trên các ứng dụng web có dữ liệu được quản lý bằng các hệ quản trị cơ sở dữ liệu như SQL Server, MySQL, Oracle, DB2, Sysbase...\n \n ## 2. Một Số Dạng Tấn Công SQL Injection thông thường\n Có bốn dạng tấn công thường gặp bao gồm: vượt qua kiểm tra lúc đăng nhập, sử dụng câu lệnh SELECT, sử dụng câu lệnh INSERT, sử dụng các stored-procedures\n \n Dạng tấn công vượt qua kiểm tra lúc đăng nhập\n Với dạng tấn công này, kẻ tấn công có thể dễ dàng vượt qua các trang đăng nhập nhờ vào lỗi khi dùng các câu lệnh SQL thao tác trên cơ sở dữ liệu của ứng dụng web. Thông thường để cho phép người dùng truy cập vào các trang web được bảo mật, hệ thống thường xây dựng trang đăng nhập để yêu cầu người dùng nhập thông tin về tên đăng nhập và mật khẩu. Sau khi người dùng nhập thông tin vào, hệ thống sẽ kiểm tra tên đăng nhập và mật khẩu có hợp lệ hay không để quyết định cho phép hay từ chối thực hiện tiếp. Ví dụ như với một form login như dưới đây. Thử submit các field username, password hoặc field id, .. bằng hi\' or 1=1. Nếu thành công, thì có thể login vào mà không cần phải biết username và password.','luongnguyen',_binary '\0',0,'SQL Injection','2024-01-21 17:54:50.770000'),(4,0,'# Giới thiệu\n ResNet (Residual Network) được giới thiệu đến công chúng vào năm 2015 và thậm chí đã giành được vị trí thứ 1 trong cuộc thi ILSVRC 2015 với tỉ lệ lỗi top 5 chỉ 3.57%. Không những thế nó còn đứng vị trí đầu tiên trong cuộc thi ILSVRC and COCO 2015 với ImageNet Detection, ImageNet localization, Coco detection và Coco segmentation.Hiện tại thì có rất nhiều biến thể của kiến trúc ResNet với số lớp khác nhau như ResNet-18, ResNet-34, ResNet-50, ResNet-101, ResNet-152,...Với tên là ResNet theo sau là một số chỉ kiến trúc ResNet với số lớp nhất định.\n # Tại sao lại xuất hiện mạng ResNet\n Mạng ResNet (R) là một mạng CNN được thiết kế để làm việc với hàng trăm lớp. Một vấn đề xảy ra khi xây dựng mạng CNN với nhiều lớp chập sẽ xảy ra hiện tượng Vanishing Gradient dẫn tới quá trình học tập không tốt.\n # Vanishing Gradient\n Trước hết thì Backpropagation Algorithm là một kỹ thuật thường được sử dụng trong quá trình tranining. Ý tưởng chung của thuật toán lá sẽ đi từ output layer đến input layer và tính toán gradient của cost function tương ứng cho từng parameter (weight) của mạng. Gradient Descent sau đó được sử dụng để cập nhật các parameter đó.\n Toàn bộ quá trình trên sẽ được lặp đi lặp lại cho tới khi mà các parameter của network được hội tụ. Thông thường chúng ta sẽ có một hyperparametr (số Epoch - số lần mà traninig set được duyệt qua một lần và weights được cập nhật) định nghĩa cho số lượng vòng lặp để thực hiện quá trình này. Nếu số lượng vòng lặp quá nhỏ thì ta gặp phải trường hợp mạng có thể sẽ không cho ra kết quả tốt và ngược lại thời gian tranining sẽ lâu nếu số lượng vòng lặp quá lớn.\n Tuy nhiên, trong thực tế Gradients thường sẽ có giá trị nhỏ dần khi đi xuống các layer thấp hơn. Dẫn đến kết quả là các cập nhật thực hiện bởi Gradients Descent không làm thay đổi nhiều weights của các layer đó và làm chúng không thể hội tụ và mạng sẽ không thu được kết quả tốt. Hiện tượng như vậy gọi là Vanishing Gradients.. Chỗ này hơi rối não 😂😂😂\n \n ===> Mạng ResNet ra đời cũng giải quyết vấn đề đó.\n #Kiến trúc mạng ResNet\n Cho nên giải pháp mà ResNet đưa ra là sử dụng kết nối \"tắt\" đồng nhất để xuyên qua một hay nhiều lớp. Một khối như vậy được gọi là một Residual Block.\n ResNet gần như tương tự với các mạng gồm có convolution, pooling, activation và fully-connected layer. Ảnh bên trên hiển thị khối dư được sử dụng trong mạng. Xuất hiện một mũi tên cong xuất phát từ đầu và kết thúc tại cuối khối dư. Hay nói cách khác là sẽ bổ sung Input X vào đầu ra của layer, hay chính là phép cộng mà ta thấy trong hình minh họa, việc này sẽ chống lại việc đạo hàm bằng 0, do vẫn còn cộng thêm X. Với H(x) là giá trị dự đoán, F(x) là giá trị thật (nhãn), chúng ta muốn H(x) bằng hoặc xấp xỉ F(x).\n Như chúng ta đã biết việc tăng số lượng các lớp trong mạng làm giảm độ chính xác, nhưng muốn có một kiến trúc mạng sâu hơn có thể hoạt động tốt.','luongnguyen',_binary '\0',0,'Giới thiệu mạng ResNet','2024-01-21 17:56:31.676000'),(5,0,'Cho mình hỏi trong thực tế khi gọi API từ reacjs hoặc vue ... nếu khi gọi vào 1 router private VD: api/admin chẳng hạn.\n \n Với 1 user chưa login thì nó sẽ trả về lỗi 401 ( unauthorized status code) do header họ gửi lên k có token, hoặc token hết hạn ...\n \n Nếu trả về như vậy thì trên console web brower sẽ báo error ( cá nhân mình thấy điều này không được hay cho lắm). Cho mình hỏi điều đó có chấp nhận được không? và nếu ko thì sẽ giải quyết bằng cách nào.\n \n liệu có nên sửa lại response bên backend(laravel không) Status Code: 200 OK\n ','luongnguyen',_binary '\0',0,'Cách xử lý error khi gọi Private router trong reacjs khi goi API (gọi API từ reacjs,vue)','2024-01-21 17:57:27.583000'),(6,0,'Tôi muốn tăng dung lượng Ram vào docker destop để tăng tốc nó chạy thì làm sao','luongnguyen',_binary '\0',0,'Docker','2024-01-21 17:58:03.289000'),(7,0,'Nên chọn ngôn ngữ nào làm backend em đang phân vân các stack sau mern , java spring boot , php laravel?','luongnguyen',_binary '\0',0,'Nên chọn ngôn ngữ nào làm backend?','2024-01-21 17:58:33.146000'),(8,0,'Em muốn tắt chức năng devtools bằng js( hoặc gì cũng được) cho dự án nextjs. Em có tham khảo cái \"devTools: false\" trong file next.config.js nhưng nó không hoạt động. Đây là phần em thêm vào\n Có giải pháp gì không ạ','luongnguyen',_binary '\0',0,'Hỏi cách chống bật devtools trên browser bằng Nextjs','2024-01-21 17:59:34.335000'),(9,0,'Hiện nay đa phần trong các dự án kiểm thử tester không còn chỉ đơn thuần làm những nhiệm vụ testing nữa mà còn đòi hỏi cả kỹ năng code. Vậy những bạn đang có dự định tìm kiếm các khóa học tester thì có khóa học tester dành cho người mới bắt đầu nào có dạy kèm code không á?','luongnguyen',_binary '\0',0,'Khóa học tester dành cho người mới bắt đầu ở đâu dạy có kèm code','2024-01-21 18:00:02.685000');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series`
--

DROP TABLE IF EXISTS `series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `series` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comment_count` int NOT NULL,
  `content` longtext NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `is_private` bit(1) NOT NULL,
  `score` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series`
--

LOCK TABLES `series` WRITE;
/*!40000 ALTER TABLE `series` DISABLE KEYS */;
INSERT INTO `series` VALUES (1,0,'# Giới thiệu\n Xin chào tất cả các bạn, mình là loda. Hôm nay mình sẽ giới thiệu tới các bạn khóa học Java căn bản trong 2 tuần. Mục tiêu là giúp các bạn, dù xuất phát điểm như nào cũng có thể nắm được ngôn ngữ này trong 2 tuần 😗 (rìa lý men)\n \n Mặc dù mục đích ban đầu blog của mình hướng tới là chia sẻ các kiến thức nâng cao về Java, Spring, v.v.. Vì bản thân mình nghĩ các kiến thức cơ bản thì có quá nhiều nguồn để học rồi 😳 ấy vậy mà không như thế các bạn ạ 😳 . Chính vì nhiều nguồn quá rồi, nó lại khiến các bạn bối rối giữa việc chọn nguồn tài liệu và giáo trình, dẫn tới học chắp và, không hiệu quả, và thường mất thời gian mới hiểu được bản chất ngôn ngữ này, vì theo mình khảo sát thì các bài viết không đầu tư đủ nội dung, thường là \"mì ăn liền\". Thứ 2, cũng là mục đích chính, có bạn muốn mình giúp đỡ nên mình quyết định viết series này. :v ha ha .. ha\n - Series: Java Basic\n - Số bài: 14 ~ 18','luongnguyen',_binary '\0',0,'[JAV0] Series Java căn bản.','2024-01-21 18:02:00.085000'),(2,0,'Chào mừng các bạn đã đến với bài 8 của series Mạng Căn Bản. Hôm nay chúng ta sẽ tìm hiểu về các hình thức tấn công mạng phổ biến theo từng lớp của mô hình TCP/IP. Đây cũng là bài tạm kết thúc của series Mạng Căn Bản. Tại sao gọi là tạm kết thúc? Vì hiện tại với ngần này kiến thức cá nhân mình thấy đã phần nào đủ cho các bạn để theo series Linux Penetration Testing căn bản rồi. Trong tương lai nếu mình thấy có những nội dung cần thiết ví dụ như Virtualization network (mạng ảo), mình sẽ viết tiếp series này.','luongnguyen',_binary '\0',0,'Series Mạng Căn Bản','2024-01-21 18:02:33.878000'),(3,0,'SQL(Structured Query Language) là một ngôn ngữ tiêu chuẩn để lưu trữ, thao tác và truy xuất dữ liệu trong cơ sở dữ liệu. Series này sẽ dạy bạn cách sử dụng SQL trong các hệ quản trị cơ sở sau đây: MySQL, SQL Server, MS Access, Oracle, Sybase, Informix, Postgres, và các hệ thống cơ sở dữ liệu khác.','luongnguyen',_binary '\0',0,'SQL Series','2024-01-21 18:03:24.044000');
/*!40000 ALTER TABLE `series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series_posts`
--

DROP TABLE IF EXISTS `series_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `series_posts` (
  `post_id` int NOT NULL,
  `series_id` int NOT NULL,
  PRIMARY KEY (`post_id`,`series_id`),
  KEY `FK7gvy874lxrvdesf9mksl9i1u` (`series_id`),
  CONSTRAINT `FK7gvy874lxrvdesf9mksl9i1u` FOREIGN KEY (`series_id`) REFERENCES `series` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKdey80vbk8lrrxgf3i8f8gb6x2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series_posts`
--

LOCK TABLES `series_posts` WRITE;
/*!40000 ALTER TABLE `series_posts` DISABLE KEYS */;
INSERT INTO `series_posts` VALUES (1,1),(2,1),(4,2),(3,3);
/*!40000 ALTER TABLE `series_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t48xdq560gs3gap9g7jg36kgc` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Câu hỏi/Thắc mắc cần được giải đáp','HoiDap'),(2,'Kiến thức/Câu hỏi về NodeJs','NodeJs'),(3,'Kinh nghiệm/Thắc mắc về thị trường tuyển dụng IT','ViecLam'),(4,'Kiến thức/Câu hỏi về Flutter','Flutter'),(5,'Kiến thức/Câu hỏi về SQL','SQL'),(6,'Chia sẻ/Bàn luận về các phần mềm trên máy tính','PhanMem'),(7,'Kiến thức/Chia sẻ về mạng','Mang'),(8,'Cập nhật kiến thức, công nghệ Blockchain','Blockchain'),(9,'Câu chuyện của dev','TamSu'),(10,'Tình yêu','TinhYeu'),(11,'Chủ đề về ngôn ngữ Java','Java'),(12,'Chủ đề về framework Django','Django'),(13,'Chủ đề về thuật toán','Algorithm'),(14,'Chủ đề về Deep Learning trong khoa học máy tính','DeepLearning'),(15,'Chủ đề về JavaScript','JavaScript'),(16,'Kiến thức/Câu hỏi xoay quanh lập trình viên Web','WebDev'),(17,'Chủ đề về lập trình nhúng','Embedded'),(18,'Đời sống, xã hội','DoiSong'),(19,'Kiến thức về React','React'),(20,'Chủ đề về kiểm thử','Testing');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-22  1:16:03
