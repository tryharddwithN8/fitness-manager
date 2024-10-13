-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS fitness_db;

-- Kiểm tra và tạo user nếu chưa tồn tại
DELIMITER //
CREATE PROCEDURE CreateUserIfNotExists()
BEGIN
    DECLARE user_exists INT DEFAULT 0;
    
    -- Kiểm tra xem user có tồn tại không
    SELECT COUNT(*) INTO user_exists
    FROM mysql.user
    WHERE user = 'apt' AND host = '%';
    
    -- Nếu user không tồn tại, tạo user và cấp quyền
    IF user_exists = 0 THEN
        -- Tạo người dùng và yêu cầu kết nối qua SSL
        CREATE USER 'apt'@'%' IDENTIFIED BY 'PASS' REQUIRE SSL;

        -- Cấp toàn quyền cho người dùng này với cơ sở dữ liệu vừa tạo
        GRANT ALL PRIVILEGES ON fitness_db.* TO 'apt'@'%';

        -- Áp dụng các thay đổi về quyền
        FLUSH PRIVILEGES;
    END IF;
END //
DELIMITER ;

CALL CreateUserIfNotExists();


USE fitness_db;

-- Bảng users: Lưu thông tin người dùng
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Lưu trữ mật khẩu băm (hashed)
    email VARCHAR(100) NOT NULL,
    role ENUM('user', 'coach', 'admin') DEFAULT 'user',
    address VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng coaches: Lưu thông tin huấn luyện viên
CREATE TABLE coaches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,  -- Tham chiếu đến bảng users
    bio TEXT,  -- Thông tin chi tiết về huấn luyện viên
    experience VARCHAR(255),  -- Kinh nghiệm
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bảng courses: Lưu thông tin về các khóa học
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    coach_id INT,  -- Tham chiếu đến bảng coaches
    course_name VARCHAR(100) NOT NULL,
    description TEXT,  -- Mô tả khóa học
    difficulty_level ENUM('beginner', 'intermediate', 'advanced') NOT NULL,  -- Mức độ khó
    duration_weeks INT,  -- Thời lượng khóa học tính theo tuần
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (coach_id) REFERENCES coaches(id)
);

-- Bảng workouts: Lưu thông tin về các bài tập trong mỗi khóa học
CREATE TABLE workouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT,  -- Tham chiếu đến bảng courses
    workout_name VARCHAR(100) NOT NULL,
    description TEXT,  -- Mô tả bài tập
    duration_minutes INT,  -- Thời lượng bài tập tính bằng phút
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Bảng subscriptions: Lưu thông tin về việc người dùng đăng ký khóa học
CREATE TABLE subscriptions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,  -- Tham chiếu đến bảng users
    course_id INT,  -- Tham chiếu đến bảng courses
    subscribed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Bảng progress: Theo dõi tiến độ của người dùng trong khóa học
CREATE TABLE progress (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,  -- Tham chiếu đến bảng users
    course_id INT,  -- Tham chiếu đến bảng courses
    workout_id INT,  -- Tham chiếu đến bảng workouts
    status ENUM('not_started', 'in_progress', 'completed') DEFAULT 'not_started',  -- Trạng thái bài tập
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (workout_id) REFERENCES workouts(id)
);

-- Bảng schedules: Lưu lịch tập của người dùng
CREATE TABLE schedules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,  -- Tham chiếu đến bảng users
    workout_id INT,  -- Tham chiếu đến bảng workouts
    scheduled_time DATETIME,  -- Thời gian đã lên lịch cho bài tập
    reminder_time DATETIME,  -- Thời gian nhắc nhở
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (workout_id) REFERENCES workouts(id)
);
