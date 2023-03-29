create table if not exists Socks
(
    socks_color  varchar(256) not null default 'Нет цвета',
    socks_cotton integer          not null default 0
    CHECK (socks_cotton > 0) CHECK ( socks_cotton <= 100 ),
    PRIMARY KEY (socks_color, socks_cotton),
    socks_count  integer          not null default 1
    CHECK (socks_count > 0)
);








