CREATE TABLE User(
        UserId int,
        Username varchar(255) NOT NULL UNIQUE,
        Password varchar(255) NOT NULL,
        Token varchar(255) NOT NULL,
PRIMARY KEY(UserId)
);

CREATE TABLE Product(
        ProductId int,
        ProductCode varchar(255) NOT NULL,
        BarcodeFormat varchar(255) NOT NULL,
        Name varchar(255) NOT NULL,
PRIMARY KEY(ProductId)
);

CREATE TABLE Recipe(
        RecipeId int NOT NULL,
        Title varchar(255) NOT NULL,
        Description varchar(255),
        ImagePath varchar(255),
        UserId int NOT NULL,
PRIMARY KEY(RecipeId)
);

CREATE TABLE RecipeProduct(
        RecipeId int NOT NULL,
        ProductId int NOT NULL,
        Unit varchar(255) NOT NULL,
        Value float NOT NULL,
PRIMARY KEY(RecipeId, ProductId)
);  
    
ALTER TABLE Recipe ADD FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE RESTRICT;
ALTER TABLE RecipeProduct ADD FOREIGN KEY (RecipeId) REFERENCES Recipe(RecipeId) ON DELETE CASCADE;
ALTER TABLE RecipeProduct ADD FOREIGN KEY (ProductId) REFERENCES Product(ProductId) ON DELETE CASCADE;

