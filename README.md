# Football Manager
Chương trình Quản lý Giải đấu Bóng đá được xây dựng nhằm tự động hóa toàn bộ quy trình tổ chức và vận hành các giải đấu bóng đá quy mô vừa và nhỏ, thay thế phương thức quản lý thủ công bằng một hệ thống chính xác, đồng bộ và tuân thủ chặt chẽ thể lệ thi đấu cố định.

# Giới thiệu
Project này được xây dựng bằng Java (Maven) và sử dụng MySQL làm hệ quản trị cơ sở dữ liệu. Cơ sở dữ liệu của hệ thống được khởi tạo tự động thông qua file SQL đi kèm (football_manager_init.sql), giúp thiết lập đầy đủ các bảng và ràng buộc cần thiết ngay từ đầu.
Ứng dụng được phát triển nhằm tự động hóa quy trình quản lý các giải đấu bóng đá quy mô vừa và nhỏ, nơi mà các nghiệp vụ như ghi danh đội bóng, lập lịch thi đấu, ghi nhận sự kiện trận đấu hay cập nhật kết quả thường được thực hiện thủ công, dễ sai sót và thiếu đồng bộ. Hệ thống tuân theo mô hình 3 lớp (Three-Tier Architecture), đảm bảo tính rõ ràng, dễ mở rộng và dễ bảo trì.
Chương trình cung cấp các chức năng trọng yếu như: quản lý đội bóng, cầu thủ, trọng tài, trận đấu; tạo và quản lý lịch thi đấu; ghi nhận các sự kiện trong trận; và tự động tính toán bảng xếp hạng dựa trên kết quả thi đấu theo đúng bộ quy tắc cố định của giải đấu. Hệ thống giúp giảm thiểu thời gian, hạn chế sai sót và nâng cao tính minh bạch trong công tác tổ chức giải.

# Công nghệ sử dụng
Hệ thống được xây dựng và vận hành dựa trên các công nghệ sau:

• Hệ điều hành
Windows 11
Dùng làm môi trường phát triển và chạy ứng dụng.

• Ngôn ngữ lập trình
Java Development Kit (JDK) 23
Cung cấp bộ công cụ biên dịch và xử lý mã nguồn Java.
Yêu cầu chạy: Java Runtime Environment (JRE) 24.

• Môi trường phát triển (IDE)
Apache NetBeans IDE 26
Hỗ trợ soạn thảo mã nguồn, quản lý dự án Maven và debugging.
Không có yêu cầu bổ sung cho runtime.

• Hệ quản trị cơ sở dữ liệu
MySQL Server 8.0
Lưu trữ và xử lý dữ liệu của hệ thống.

• Thư viện kết nối CSDL
MySQL Connector/J (JDBC) 8.0
Thư viện JDBC giúp Java tương tác với MySQL.
Yêu cầu: Phải được cấu hình trong classpath của dự án.



