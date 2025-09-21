CREATE TABLE accounts (
    id varchar(36) PRIMARY KEY,
    user_id varchar(36) NOT NULL,
    balance DECIMAL(18,2) DEFAULT 0,
    total_spent DECIMAL(18,2) DEFAULT 0,
    total_income DECIMAL(18,2) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
    id varchar(36) PRIMARY KEY,
    account_id varchar(36) NOT NULL,
    type ENUM('INCOME','EXPENSE') NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);