create table tbl_user
(
    id            BINARY(16) primary key,
    full_name     varchar(200),
    age           INT,
    modified_by   VARCHAR(200),
    modified_date DATETIME,
    created_date  DATETIME,
    created_by    VARCHAR(200),
    is_deleted    TINYINT(1) DEFAULT 0
);
