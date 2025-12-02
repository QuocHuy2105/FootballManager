CREATE DATABASE FOOTBALL_MANAGER;

USE FOOTBALL_MANAGER;

CREATE TABLE SYSTEM_CONFIG (
	config_key VARCHAR(50) PRIMARY KEY NOT NULL,
	config_value VARCHAR(50) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT='Bảng lưu thông tin giải đấu';

INSERT INTO SYSTEM_CONFIG (config_key) 
VALUES ('tournament_exists'),
('tournament_name');

CREATE TABLE TEAMS (
	team_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id của đội bóng',
    
    team_name VARCHAR(50) COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT 'Tên của đội bóng', 
    department VARCHAR(50) COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT 'Phòng ban mà đội bóng thuộc về',
    coach_name VARCHAR(50) COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT 'Tên huấn luyện viên của đội bóng',
    
    is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = hoạt động, 0 = đã xóa mềm',

	UNIQUE KEY uk_team_name (team_name)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT='Bảng lưu thông tin các đội bóng';

CREATE TABLE PLAYERS (
	player_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id của cầu thủ',
    
    player_name VARCHAR(50) COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT 'Tên của cầu thủ',
    dob DATE NOT NULL COMMENT 'Ngày sinh của cầu thủ',
    jersey_number TINYINT NOT NULL COMMENT 'Số áo của cầu thủ (mỗi đội chỉ có một cầu thủ dùng mỗi số)',
    position ENUM('GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'FORWARD') NOT NULL COMMENT 'Vị trí chơi của cầu thủ',
    
    team_id INT NOT NULL COMMENT 'Id đội bóng mà cầu thủ thuộc về, khóa ngoại đến cột (team_id) trong bảng TEAMS',
    
    is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = hoạt động, 0 = xóa mềm',
    
    CONSTRAINT fk_player_team 
		FOREIGN KEY (team_id) REFERENCES TEAMS(team_id)
		ON UPDATE CASCADE
        ON DELETE CASCADE,
     
	CONSTRAINT chk_jersey_number
		CHECK (jersey_number BETWEEN 1 AND 99)
        
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu thông tin các cầu thủ';

CREATE TABLE REFEREES (
	referee_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id của trọng tài',
    
    referee_name VARCHAR(50) COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT 'Tên của trọng tài',
    phone_number VARCHAR(10) NOT NULL COMMENT 'Số điện thoại của trọng tài',
    
     is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = hoạt động, 0 = xóa mềm',
    
    UNIQUE KEY uk_phone_number (phone_number)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu thông tin các trọng tài';

CREATE TABLE MATCHES (
	match_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id của trận đấu',
    
    referee_id INT NOT NULL COMMENT 'Id trọng tài bắt cho trận, khóa ngoại đến cột (referee_id) của bảng REFEREES',
    match_date DATE NOT NULL COMMENT 'Ngày diễn ra của trận đấu',
    match_status ENUM('DA_DIEN_RA','CHUA_DIEN_RA') DEFAULT 'CHUA_DIEN_RA' COMMENT 'Trạng thái hiện tại của trận đấu',
    
    CONSTRAINT fk_match_referee
		FOREIGN KEY (referee_id) REFERENCES REFEREES(referee_id)
		ON UPDATE CASCADE
        ON DELETE CASCADE
) 
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu thông tin các trận đấu';

CREATE TABLE MATCH_TEAMS (
	match_id INT NOT NULL COMMENT 'Id của trận đấu',
    team_id INT NOT NULL COMMENT 'Id của đội bóng tham gia vào trận đấu',
    team_role ENUM ('HOME','AWAY') NOT NULL COMMENT 'Vai trò của đội bóng tham gia vào trận (đội nhà hoặc đội khách)',
    
    CONSTRAINT pk_match_teams 
		PRIMARY KEY (match_id, team_id),
        
	CONSTRAINT fk_match_teams
		FOREIGN KEY (match_id) REFERENCES MATCHES(match_id)
		ON UPDATE CASCADE
        ON DELETE CASCADE,
        
	CONSTRAINT fk_team_match
		FOREIGN KEY (team_id) REFERENCES TEAMS(team_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu thông tin đội nhà và đội khách sẽ tham gia vào trận đấu';


CREATE TABLE MATCH_PLAYERS (
	match_id INT NOT NULL COMMENT 'Id của trận đấu',
    player_id INT NOT NULL COMMENT 'Id của cầu thủ tham gia vào trận đấu',
    player_role ENUM ('STARTING', 'SUBSTITUTE') NOT NULL COMMENT 'Vai trò của cầu thủ khi tham gia vào trận đấu (đá chính hoặc dự bị) ',
    position_in_match ENUM('GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'FORWARD') NOT NULL COMMENT 'Vị trí của cầu thủ khi tham gia trận đấu',
    
    CONSTRAINT pk_match_players
		PRIMARY KEY (match_id, player_id),
        
	CONSTRAINT fk_match_players
		FOREIGN KEY (match_id) REFERENCES MATCHES(match_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
        
	CONSTRAINT fk_player_match
		FOREIGN KEY (player_id) REFERENCES PLAYERS(player_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu thông tin danh sách cầu thủ sẽ tham gia vào trận đấu';

CREATE TABLE MATCH_EVENTS (
	event_id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Id của sự kiện',
    
    match_id INT NOT NULL COMMENT 'Id của trận đấu mà sự kiện xảy ra',
    player_id INT NULL COMMENT 'Id của cầu thủ có liên qua đến sự kiện (sự kiện MATCH_START và MATCH_END không có cầu thủ liên quan)',
    event_type ENUM('GOAL', 'OWN_GOAL', 'PENALTY_GOAL', 'YELLOW_CARD', 'RED_CARD', 'SUBSTITUTION_OUT', 'SUBSTITUTION_IN', 'FOUL', 'MATCH_START', 'MATCH_END') NOT NULL COMMENT 'Các loại sự kiện',
    event_time TINYINT NOT NULL COMMENT 'Thời gian xảy ra sự kiện',
    
    CONSTRAINT fk_match_events
		FOREIGN KEY (match_id) REFERENCES MATCHES(match_id)
		ON UPDATE CASCADE
        ON DELETE CASCADE,
        
	CONSTRAINT fk_player_event
		FOREIGN KEY (player_id) REFERENCES PLAYERS(player_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
        
	CONSTRAINT chk_event_time
		CHECK (event_time BETWEEN 0 AND 60)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_as_ci
COMMENT = 'Bảng lưu các sự kiện xảy ra trong trận đấu và cầu thủ liên quan đếu sự kiện đó (nếu có)';


-- Trigger tự động xóa cứng các dữ liệu có liên quan đến cầu thủ trong các trận đấu chưa diễn ra
DELIMITER $$

CREATE TRIGGER tg_soft_delete_player_cleanup
AFTER UPDATE ON PLAYERS
FOR EACH ROW
BEGIN
    -- Chỉ chạy khi xóa mềm
    IF OLD.is_active = 1 AND NEW.is_active = 0 THEN

        DELETE me
        FROM MATCH_EVENTS me
        JOIN MATCH_PLAYERS mp_player
          ON mp_player.match_id = me.match_id
         AND mp_player.player_id = OLD.player_id
        JOIN MATCHES m
          ON m.match_id = me.match_id
        WHERE m.match_status = 'CHUA_DIEN_RA';

        DELETE mp_all
        FROM MATCH_PLAYERS mp_all
        JOIN MATCH_PLAYERS mp_player
          ON mp_player.match_id = mp_all.match_id
         AND mp_player.player_id = OLD.player_id
        JOIN MATCHES m2
          ON m2.match_id = mp_all.match_id
        WHERE m2.match_status = 'CHUA_DIEN_RA'
          AND mp_all.player_id IN (
                SELECT p.player_id
                FROM PLAYERS p
                WHERE p.team_id = OLD.team_id
          );

    END IF;
END $$

DELIMITER ;

-- Trigger tự động xóa cứng các dữ liệu có liên quan đến đội bóng trong các trận đấu chưa diễn ra
DELIMITER $$

CREATE TRIGGER tg_soft_delete_team_cleanup
AFTER UPDATE ON TEAMS
FOR EACH ROW
BEGIN
    -- Chỉ chạy khi is_active chuyển từ 1 → 0 (soft delete)
    IF OLD.is_active = 1 AND NEW.is_active = 0 THEN

		-- Xóa mềm cho toàn bộ cầu thủ thuộc đội
        UPDATE PLAYERS
        SET is_active = 0
        WHERE team_id = OLD.team_id;

        -- Xóa tất cả dữ liệu liên quan trong các trận chưa diễn ra mà đội này tham gia
        -- Bước 1: Xóa toàn bộ sự kiện của trận
        DELETE FROM MATCH_EVENTS
        WHERE match_id IN (
            SELECT m.match_id
            FROM MATCHES m
            JOIN MATCH_TEAMS mt ON m.match_id = mt.match_id
            WHERE mt.team_id = OLD.team_id
              AND m.match_status = 'CHUA_DIEN_RA'
        );

        -- Bước 2: Xóa toàn bộ danh sách cầu thủ của cả 2 đội trong trận chưa diễn ra
        DELETE FROM MATCH_PLAYERS
        WHERE match_id IN (
            SELECT m.match_id
            FROM MATCHES m 
            JOIN MATCH_TEAMS mt ON m.match_id = mt.match_id
            WHERE mt.team_id = OLD.team_id
              AND m.match_status = 'CHUA_DIEN_RA'
        );

        -- Bước 3: Xóa toàn bộ 2 đội khỏi MATCH_TEAMS
        DELETE FROM MATCH_TEAMS
		WHERE match_id IN (
			SELECT match_id FROM (
				SELECT m.match_id
				FROM MATCHES m
				JOIN MATCH_TEAMS mt ON m.match_id = mt.match_id
				WHERE mt.team_id = OLD.team_id
				  AND m.match_status = 'CHUA_DIEN_RA'
			) AS tmp
		);

    END IF;
END $$

DELIMITER ;

-- Trigger xóa mềm trọng tài và xóa cứng toàn bộ các thông tin liên quan đến các trận đấu chưa diễn ra mà trọng tài tham gia
DELIMITER $$

CREATE TRIGGER tg_soft_delete_referee_cleanup
AFTER UPDATE ON REFEREES
FOR EACH ROW
BEGIN
    -- Chỉ chạy khi is_active chuyển từ 1 → 0 (soft delete)
    IF OLD.is_active = 1 AND NEW.is_active = 0 THEN

        -- Soft delete MATCH_EVENTS thuộc các trận chưa diễn ra
        DELETE FROM MATCH_EVENTS
        WHERE match_id IN (
            SELECT m.match_id
            FROM MATCHES m
            WHERE m.referee_id = OLD.referee_id
              AND m.match_status = 'CHUA_DIEN_RA'
        );

        -- Soft delete MATCH_PLAYERS thuộc các trận chưa diễn ra
        DELETE FROM MATCH_PLAYERS
        WHERE match_id IN (
            SELECT m.match_id
            FROM MATCHES m
            WHERE m.referee_id = OLD.referee_id
              AND m.match_status = 'CHUA_DIEN_RA'
        );

        -- Soft delete MATCH_TEAMS thuộc các trận chưa diễn ra
        DELETE FROM MATCH_TEAMS
        WHERE match_id IN (
            SELECT m.match_id
            FROM MATCHES m
            WHERE m.referee_id = OLD.referee_id
              AND m.match_status = 'CHUA_DIEN_RA'
        );

        -- Soft delete MATCHES (chính trận đấu)
        DELETE FROM MATCHES
        WHERE referee_id = OLD.referee_id
          AND match_status = 'CHUA_DIEN_RA';

    END IF;
END$$

DELIMITER ;

-- Trigger đảm bảo một trận đấu có đúng 1 đội nhà và 1 đội khách
DELIMITER $$

CREATE TRIGGER tg_match_teams_limit
BEFORE INSERT ON MATCH_TEAMS
FOR EACH ROW
BEGIN
    -- HOME CHECK
    IF NEW.team_role = 'HOME' AND 
       EXISTS(SELECT 1 FROM MATCH_TEAMS 
              WHERE match_id = NEW.match_id AND team_role = 'HOME') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This match already has a HOME team';
    END IF;

    -- AWAY CHECK
    IF NEW.team_role = 'AWAY' AND 
       EXISTS(SELECT 1 FROM MATCH_TEAMS 
              WHERE match_id = NEW.match_id AND team_role = 'AWAY') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This match already has an AWAY team';
    END IF;
END $$

DELIMITER ;

-- Trigger cập nhập trạng thái của trận đấu 
DELIMITER $$

CREATE TRIGGER tg_event_set_match_status
AFTER INSERT ON MATCH_EVENTS
FOR EACH ROW
BEGIN
    IF NEW.event_type = 'MATCH_END' THEN
        UPDATE MATCHES SET match_status = 'DA_DIEN_RA'
        WHERE match_id = NEW.match_id;
    END IF;
END $$

DELIMITER ;


-- Trigger đảm bảo cầu thủ được ghi event phải đang tham gia vào trận đấu đó
DELIMITER $$

CREATE TRIGGER tg_event_player_must_be_in_match
BEFORE INSERT ON MATCH_EVENTS
FOR EACH ROW
BEGIN
    IF NEW.player_id IS NOT NULL AND
       NOT EXISTS (SELECT 1 FROM MATCH_PLAYERS 
                   WHERE match_id = NEW.match_id 
                     AND player_id = NEW.player_id) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Player is not registered in MATCH_PLAYERS for this match';
    END IF;
END $$

DELIMITER ;


-- Trigger đảm bảo cầu thủ được thêm vào danh sách thi đấu phải có cùng đội trùng với đội của trận đó
DELIMITER $$

CREATE TRIGGER tg_match_players_team_validation
BEFORE INSERT ON MATCH_PLAYERS
FOR EACH ROW
BEGIN
    IF NOT EXISTS(
        SELECT 1 
        FROM MATCH_TEAMS mt
        JOIN PLAYERS p ON p.team_id = mt.team_id
        WHERE mt.match_id = NEW.match_id
          AND p.player_id = NEW.player_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Player team does not match any team in this match';
    END IF;
END $$

DELIMITER ;


-- Function dùng để đếm số bàn thắng của đội trong trận đấu
DELIMITER $$

CREATE FUNCTION get_team_goals(matchId INT, teamId INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE goals INT DEFAULT 0;

    SELECT 
        COALESCE(
            SUM(
                CASE 
                    -- 1) Bàn thắng (goal hoặc penalty_goal) của cầu thủ thuộc đội này
                    WHEN me.event_type IN ('GOAL', 'PENALTY_GOAL')
                         AND p.team_id = teamId
                    THEN 1

                    -- 2) Own goal của đội đối phương → tính cho teamId
                    WHEN me.event_type = 'OWN_GOAL'
                         AND p.team_id IS NOT NULL
                         AND p.team_id <> teamId
                    THEN 1

                    ELSE 0
                END
            ),
        0)
    INTO goals
    FROM match_events me
    LEFT JOIN players p ON me.player_id = p.player_id
    WHERE me.match_id = matchId;

    RETURN goals;
END $$

DELIMITER ;



DELIMITER $$

CREATE FUNCTION is_match_ready(matchId INT) 
RETURNS tinyint(1)
DETERMINISTIC
BEGIN
    DECLARE teamCount INT DEFAULT 0;
    DECLARE homeCount INT DEFAULT 0;
    DECLARE awayCount INT DEFAULT 0;
    DECLARE playerCount INT DEFAULT 0;

    -- Kiểm tra số đội tham gia
    SELECT 
        COUNT(DISTINCT team_id),
        SUM(team_role = 'HOME'),
        SUM(team_role = 'AWAY')
    INTO teamCount, homeCount, awayCount
    FROM match_teams
    WHERE match_id = matchId;

    IF teamCount <> 2 OR homeCount <> 1 OR awayCount <> 1 THEN
        RETURN FALSE;
    END IF;

    -- Kiểm tra tổng số cầu thủ (bao gồm cả đá chính và dự bị)
    SELECT COUNT(mp.player_id)
    INTO playerCount
    FROM match_players mp
    JOIN players p ON mp.player_id = p.player_id
    JOIN match_teams mt ON p.team_id = mt.team_id AND mp.match_id = mt.match_id
    WHERE mp.match_id = matchId;

    IF playerCount = 22 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END $$

DELIMITER ;


-- Function đếm tổng số bàn thắng của đội
DELIMITER $$

CREATE FUNCTION get_team_total_goals(teamId INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE totalGoals INT DEFAULT 0;
    DECLARE ownGoals INT DEFAULT 0;
    DECLARE scoredGoals INT DEFAULT 0;

    -- 1) Tổng own_goal ghi cho teamId
    SELECT 
        COUNT(*)
    INTO ownGoals
    FROM match_events me
    JOIN match_teams a ON me.match_id = a.match_id
    JOIN match_teams b ON me.match_id = b.match_id AND a.team_id < b.team_id
    JOIN players p ON me.player_id = p.player_id
    WHERE me.event_type = 'OWN_GOAL'
      AND (
            (a.team_id = teamId AND p.team_id = b.team_id) OR
            (b.team_id = teamId AND p.team_id = a.team_id)
          );

    -- 2) Tổng bàn thắng ghi trực tiếp (goal, penalty_goal)
    SELECT 
        COUNT(*)
    INTO scoredGoals
    FROM match_events me
    JOIN players p ON me.player_id = p.player_id
    WHERE me.event_type IN ('GOAL', 'PENALTY_GOAL')
      AND p.team_id = teamId;

    -- 3) Kết quả cuối
    SET totalGoals = ownGoals + scoredGoals;

    RETURN totalGoals;
END $$

DELIMITER ;


-- Store Procedure Lấy thông tin của trận đấu đã diễn ra
DELIMITER $$

CREATE PROCEDURE get_details_of_match(IN matchId INT)
BEGIN
    SELECT
        m.match_id,
        m.referee_id,
        home.team_id AS home_team,
        away.team_id AS away_team,

        -- Số bàn của đội nhà
        COALESCE(
            SUM(
                CASE
                    WHEN p.team_id = home.team_id 
                         AND me.event_type IN ('GOAL', 'PENALTY_GOAL') 
                    THEN 1

                    WHEN p.team_id = away.team_id 
                         AND me.event_type = 'OWN_GOAL'
                    THEN 1

                    ELSE 0
                END
            ), 0
        ) AS home_goals,

        -- Số bàn của đội khách
        COALESCE(
            SUM(
                CASE
                    WHEN p.team_id = away.team_id 
                         AND me.event_type IN ('GOAL', 'PENALTY_GOAL') 
                    THEN 1

                    WHEN p.team_id = home.team_id 
                         AND me.event_type = 'OWN_GOAL'
                    THEN 1

                    ELSE 0
                END
            ), 0
        ) AS away_goals,

        m.match_date
    FROM matches AS m
    JOIN match_teams AS home 
        ON m.match_id = home.match_id 
       AND home.team_role = 'HOME'

    JOIN match_teams AS away 
        ON m.match_id = away.match_id 
       AND away.team_role = 'AWAY'

    LEFT JOIN match_events AS me 
        ON m.match_id = me.match_id

    LEFT JOIN players AS p 
        ON me.player_id = p.player_id

    WHERE 
        m.match_id = matchId
        AND m.match_status = 'DA_DIEN_RA'

    GROUP BY 
        m.match_id,
        m.referee_id,
        home.team_id,
        away.team_id,
        m.match_date;
END $$

DELIMITER ;

-- Store Procedure lấy thông tin cho toàn bộ các trận đấu đã diễn ra
DELIMITER $$

CREATE PROCEDURE get_all_finished_match_details()
BEGIN
    SELECT
        m.match_id,
        m.referee_id,
        home.team_id AS home_team,
        away.team_id AS away_team,

        -- Số bàn đội nhà
        COALESCE(
            SUM(
                CASE
                    WHEN p.team_id = home.team_id 
                         AND me.event_type IN ('GOAL','PENALTY_GOAL')
                    THEN 1

                    WHEN p.team_id = away.team_id 
                         AND me.event_type = 'OWN_GOAL'
                    THEN 1

                    ELSE 0
                END
            ), 0
        ) AS home_goals,

        -- Số bàn đội khách
        COALESCE(
            SUM(
                CASE
                    WHEN p.team_id = away.team_id 
                         AND me.event_type IN ('GOAL','PENALTY_GOAL')
                    THEN 1

                    WHEN p.team_id = home.team_id 
                         AND me.event_type = 'OWN_GOAL'
                    THEN 1

                    ELSE 0
                END
            ), 0
        ) AS away_goals,

        m.match_date

    FROM matches AS m

    JOIN match_teams AS home
        ON m.match_id = home.match_id
       AND home.team_role = 'HOME'

    JOIN match_teams AS away
        ON m.match_id = away.match_id
       AND away.team_role = 'AWAY'

    LEFT JOIN match_events AS me
        ON m.match_id = me.match_id

    LEFT JOIN players AS p
        ON me.player_id = p.player_id

    WHERE 
        m.match_status = 'DA_DIEN_RA'

    GROUP BY 
        m.match_id,
        m.referee_id,
        home.team_id,
        away.team_id,
        m.match_date;
END $$

DELIMITER ;
