CREATE TABLE tasks(
  id UUID NOT NULL PRIMARY KEY,
  title NOT NULL VARCHAR(50),
  description TEXT,
  is_completed NOT NULL BOOLEAN,
  start_at NOT NULL DATE,
  end_at  NOT NULL DATE,
  priority NOT NULL NUMERIC,
  user_id NOT NULL UUID,
  created_at NOT NULL DATE 
);
