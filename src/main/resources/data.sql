INSERT INTO video_pricing (
    type,
    price
)
VALUES (
    'PREMIUM',
    40
);

INSERT INTO video_pricing (
    type,
    price
)
VALUES (
    'BASIC',
    30
);

INSERT INTO video_release (name) VALUES ('NEW');
INSERT INTO video_release (name) VALUES ('REGULAR');
INSERT INTO video_release (name) VALUES ('OLD');

INSERT INTO video_pricing_strategy (
    release_name,
    period_start,
    period_end,
    type,
    charge
)
VALUES (
    'NEW',
    0,
    null,
    'PREMIUM',
    'DAILY'
);

INSERT INTO video_pricing_strategy (
    release_name,
    period_start,
    period_end,
    type,
    charge
)
VALUES (
    'REGULAR',
    0,
    3,
    'BASIC',
    'PERIODIC'
);

INSERT INTO video_pricing_strategy (
    release_name,
    period_start,
    period_end,
    type,
    charge
)
VALUES (
    'REGULAR',
    3,
    null,
    'BASIC',
    'DAILY'
);

INSERT INTO video_pricing_strategy (
    release_name,
    period_start,
    period_end,
    type,
    charge
)
VALUES (
    'OLD',
    0,
    5,
    'BASIC',
    'PERIODIC'
);

INSERT INTO video_pricing_strategy (
    release_name,
    period_start,
    period_end,
    type,
    charge
)
VALUES (
    'OLD',
    5,
    null,
    'BASIC',
    'DAILY'
);

INSERT INTO video (
    name,
    release_name
)
VALUES (
    'Matrix 11',
    'NEW'
);

INSERT INTO video (
    name,
    release_name
)
VALUES (
    'Spider Man',
    'REGULAR'
);

INSERT INTO video (
    name,
    release_name
)
VALUES (
    'Spider Man 2',
    'REGULAR'
);

INSERT INTO video (
    name,
    release_name
)
VALUES (
    'Out of Africa',
    'OLD'
);



