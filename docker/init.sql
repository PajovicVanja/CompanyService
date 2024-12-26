CREATE DATABASE IF NOT EXISTS company;

USE company;

-- Table: company
CREATE TABLE company (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         description TEXT,
                         id_sms_notification BIGINT,
                         id_picture TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         id_auth_user CHAR(36),
                         uuid_url CHAR(36),
                         address VARCHAR(255),
                         phone_number VARCHAR(20),
                         email VARCHAR(255),
                         first_name VARCHAR(255),
                         last_name VARCHAR(255),
                         company_name VARCHAR(255),
                         id_location BIGINT
);

-- Table: location
CREATE TABLE location (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          name VARCHAR(255),
                          number INT,
                          id_location BIGINT
);

-- Table: services
CREATE TABLE services (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          id_category BIGINT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          id_company BIGINT,
                          name VARCHAR(255),
                          description TEXT,
                          price FLOAT,
                          id_picture TEXT,
                          duration SMALLINT
);

-- Table: service_categories
CREATE TABLE service_categories (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    name VARCHAR(255),
                                    id_company BIGINT
);

-- Table: business_hours
CREATE TABLE business_hours (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                day_number INT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                time_from TIME,
                                time_to TIME,
                                pause_to TIME,
                                pause_from TIME,
                                id_company BIGINT,
                                day VARCHAR(50)
);

-- Table: sms_notification_config
CREATE TABLE sms_notification_config (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         name VARCHAR(255)
);

-- Foreign key constraints
ALTER TABLE company
    ADD FOREIGN KEY (id_sms_notification) REFERENCES sms_notification_config(id),
ADD FOREIGN KEY (id_location) REFERENCES location(id);

ALTER TABLE services
    ADD FOREIGN KEY (id_category) REFERENCES service_categories(id),
ADD FOREIGN KEY (id_company) REFERENCES company(id);

ALTER TABLE service_categories
    ADD FOREIGN KEY (id_company) REFERENCES company(id);

ALTER TABLE business_hours
    ADD FOREIGN KEY (id_company) REFERENCES company(id);
