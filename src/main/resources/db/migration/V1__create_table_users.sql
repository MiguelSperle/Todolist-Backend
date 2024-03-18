CREATE TABLE users (
   id TEXT PRIMARY KEY NOT NULL,
   name TEXT NOT NULL,
   email TEXT NOT NULL,
   avatar TEXT,
   password TEXT NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);