# Football Manager
Chương trình Quản lý Giải đấu Bóng đá được xây dựng nhằm tự động hóa toàn bộ quy trình tổ chức và vận hành các giải đấu bóng đá quy mô vừa và nhỏ, thay thế phương thức quản lý thủ công bằng một hệ thống chính xác, đồng bộ và tuân thủ chặt chẽ thể lệ thi đấu cố định.

# Giới thiệu
Project này được xây dựng bằng Java (Maven) và sử dụng MySQL làm hệ quản trị cơ sở dữ liệu. Cơ sở dữ liệu của hệ thống được khởi tạo tự động thông qua file SQL đi kèm (football_manager_init.sql), giúp thiết lập đầy đủ các bảng và ràng buộc cần thiết ngay từ đầu.
Ứng dụng được phát triển nhằm tự động hóa quy trình quản lý các giải đấu bóng đá quy mô vừa và nhỏ, nơi mà các nghiệp vụ như ghi danh đội bóng, lập lịch thi đấu, ghi nhận sự kiện trận đấu hay cập nhật kết quả thường được thực hiện thủ công, dễ sai sót và thiếu đồng bộ. Hệ thống tuân theo mô hình 3 lớp (Three-Tier Architecture), đảm bảo tính rõ ràng, dễ mở rộng và dễ bảo trì.
Chương trình cung cấp các chức năng trọng yếu như: quản lý đội bóng, cầu thủ, trọng tài, trận đấu; tạo và quản lý lịch thi đấu; ghi nhận các sự kiện trong trận; và tự động tính toán bảng xếp hạng dựa trên kết quả thi đấu theo đúng bộ quy tắc cố định của giải đấu. Hệ thống giúp giảm thiểu thời gian, hạn chế sai sót và nâng cao tính minh bạch trong công tác tổ chức giải.

# Công nghệ sử dụng
Hệ thống được xây dựng và vận hành dựa trên các công nghệ sau:

• Hệ điều hành:  
Windows 11.
Dùng làm môi trường phát triển và chạy ứng dụng.

• Ngôn ngữ lập trình:
Java Development Kit (JDK) 23.
Cung cấp bộ công cụ biên dịch và xử lý mã nguồn Java.
Yêu cầu chạy: Java Runtime Environment (JRE) 24.

• Môi trường phát triển (IDE):
Apache NetBeans IDE 26.
Hỗ trợ soạn thảo mã nguồn, quản lý dự án Maven và debugging.
Không có yêu cầu bổ sung cho runtime.

• Hệ quản trị cơ sở dữ liệu:
MySQL Server 8.0.
Lưu trữ và xử lý dữ liệu của hệ thống.

• Thư viện kết nối CSDL:
MySQL Connector/J (JDBC) 8.0.
Thư viện JDBC giúp Java tương tác với MySQL.
Yêu cầu: Phải được cấu hình trong classpath của dự án.

# Tính năng chính
Ứng dụng cung cấp đầy đủ các chức năng phục vụ công tác tổ chức và quản lý giải đấu bóng đá theo thể lệ cố định, bao gồm:

• Quản lý đội bóng:
Thêm, sửa, xóa đội bóng.
Tìm kiếm, quản lý cầu thủ của đội bóng.
Quản lý thông tin liên quan đến đội (tên đội, huấn luyện viên,...).

• Quản lý cầu thủ:
Thêm, sửa, xóa cầu thủ.
Tìm kiếm, xem thông só cầu thủ,...
Quản lý thông tin liên quan đến cầu thủ (tên cầu thủ, số áo, ngày sinh,..).

• Quản lý trọng tài:
Thêm, xóa, sửa thông tin trọng tài.
Gán trọng tài cho từng trận đấu.

• Quản lý trận đấu:
Tạo lịch thi đấu.
Thêm danh sách đội bóng và cầu thủ tham gia trận đấu.
Ghi sự kiện cho trận đấu.
Theo dõi trạng thái trận đấu.

• Ghi nhận sự kiện trong trận:
Bàn thắng, thay người, phạm lỗi, thẻ vàng, thẻ đỏ,...

• Bảng xếp hạng tự động:
Tự động tính điểm dựa trên kết quả trận đấu.
Xếp hạng theo điểm, hiệu số, số bàn thắng.
Đảm bảo tuân thủ đúng thể lệ riêng của chương trình.
Cập nhật theo thời gian thực sau mỗi trận.

• Import, export thông tin giải đáu:
Import danh sách đội bóng, cầu thủ, trọng tài.
Export danh sách đội bóng, cầu thủ, kết quả trận đấu, danh sách sự kiện,....

# Cài đặt & Setup
Phần này hướng dẫn chi tiết cách thiết lập và khởi chạy dự án từ đầu.

Bước 1 : Clone dự án

bash

    git clone https://github.com/your-username/football-tournament-manager.git
    cd football-tournament-manager

Bước 2 : Cài đặt cơ sở dữ liệu

Cách 1 - Dùng MySQL Workbench
  1. Mở Workbend
  2. Mở file football_manager_init.sql
  3. Nhấn Run (Ctrl + Shift + Enter)
       -> Database football_manager sẽ được tạo tự động

Cách 2 - Dùng MySQl CLI
  
bash 
  
    mysql -u root -p < football_manager_init.sql

Không cần tạo database thủ công - mọi thứ đã nằm trong script.

Bước 3 : Cấu hình thông tin trong application.properties

    url=jdbc:mysql://localhost:3306/football_manager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username=root
    password=yourpassword
    driver=com.mysql.cj.jdbc.Driver

# Cấu trúc thư mục
    project/
     ├── src/
     │    └── main/
     │         ├── java/
     │         │    └── N23DCCN058/
     │         │          └── fm/   
     │         │               ├── Main/
     │         │               ├── dao/
     │         │               ├── exception/
     │         │               ├── model/
     │         │               ├── service/
     │         │               ├── ui/
     │         │               │    ├── dialog/
     │         │               │    ├── frame/
     │         │               │    └── panel/
     │         │               └── util/
     │         │
     │         └── resources/
     │              ├── application.properties  
     │              └── com/ 
     │                   └── fm/
     │                        ├── icons/
     │                        └── pictures/
     │
     ├── football_manager_init.sql         (file tạo và khởi tạo CSDL)
     ├── pom.xml                           (cấu hình Maven)
     └── README.md

# Cách chạy chương trình

Nếu chạy bằng Mavan :

bash

    mvn clean install
    mvn exec:java

Nếu chạy từ IDE (NetBeans):
  1. Mở project.
  2. Chắc chắn đã config MySQL Connector/J.
  3. Nhấn Run Project (Shift + F6).

# Cơ sở dữ liệu

Cơ sở dữ liệu gồm các bảng : 

• TEAMS - thông tin đội bóng.
• PLAYERS - thông tin cầu thủ.
• REFEREES - thông tin trọng tài.
• MATCHES - thông tin trận đấu.
• MATCH_EVENTS - các sự kiện trong trận đấu.
• MATCH_TEAMS - danh sách đội bóng tham gia vào trận đấu.
• MATCH_PLAYERS - danh sách cầu thủ tham gia vào trận đấu.
• SYSTEM_CONFIG - cấu hình hệ thống.

File SQL
    • Tên file : football_manager_init.sql
    • Chức năng :
        • Tạo database
        • Tạo toàn bộ các table, trigger, fuction, stored procedure.



