CREATE TABLE score
(
  id                 SERIAL NOT NULL
    CONSTRAINT score_pkey
    PRIMARY KEY,
  milliseconds_spent BIGINT NOT NULL,
  player_name        VARCHAR(255)
);
