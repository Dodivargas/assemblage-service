CREATE TABLE ruling (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(256) NOT NULL
);

create table ruling_status (
	id INT AUTO_INCREMENT PRIMARY KEY,
    ruling_id INT NOT NULL,
	open_for_vote VARCHAR(1) NOT NULL,
    expiration_date DATETIME NOT NULL,
    result VARCHAR(1),  
    CONSTRAINT ruling_id
    FOREIGN KEY (ruling_id)  
    REFERENCES ruling(id)
);

ALTER TABLE ruling_status
ADD CONSTRAINT ruling_id_unique UNIQUE (ruling_id);

create table ruling_votes(
	id INT AUTO_INCREMENT PRIMARY KEY,
	ruling_status_id INT NOT NULL,
    tax_id VARCHAR(256) NOT NULL,
    in_favor VARCHAR(1) NOT NULL,
    CONSTRAINT ruling_status_id
    FOREIGN KEY (ruling_status_id)  
    REFERENCES ruling_status(id)
);

ALTER TABLE ruling_votes
ADD CONSTRAINT one_vote_for_taxid UNIQUE (ruling_status_id,tax_id);
