CREATE TABLE todos (
   id TEXT PRIMARY KEY NOT NULL,
   title TEXT NOT NULL,
   description TEXT,
   completed BOOLEAN NOT NULL DEFAULT false,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   user_id TEXT,
   CONSTRAINT user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);