CREATE TABLE IF NOT EXISTS public.mouses
(
    id integer NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Mouse_pkey" PRIMARY KEY (id),
    CONSTRAINT "Mouse_name_key" UNIQUE (name)
)