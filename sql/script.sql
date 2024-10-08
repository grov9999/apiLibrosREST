USE [dbtest03]
GO
/****** Object:  Table [dbo].[autores]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[autores](
	[id] [int] NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[biografia] [varchar](200) NULL,
	[fecha_nacimiento] [varchar](20) NULL,
	[nacionalidad] [varchar](30) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[generos]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[generos](
	[id] [int] NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[descripcion] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[libros]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[libros](
	[id] [int] NOT NULL,
	[titulo] [varchar](200) NOT NULL,
	[autorId] [int] NULL,
	[generoId] [int] NULL,
	[anioPublicacion] [int] NULL,
	[isbn] [varchar](20) NULL,
	[descripcion] [varchar](200) NULL,
	[estado] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuarios]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuarios](
	[id] [int] NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[correo] [varchar](100) NOT NULL,
	[contrasena] [varchar](100) NOT NULL,
	[rol] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[libros] ADD  DEFAULT ((0)) FOR [estado]
GO
ALTER TABLE [dbo].[usuarios] ADD  DEFAULT ('USER') FOR [rol]
GO
ALTER TABLE [dbo].[libros]  WITH CHECK ADD FOREIGN KEY([autorId])
REFERENCES [dbo].[autores] ([id])
GO
ALTER TABLE [dbo].[libros]  WITH CHECK ADD FOREIGN KEY([generoId])
REFERENCES [dbo].[generos] ([id])
GO
/****** Object:  StoredProcedure [dbo].[ListarLibrosDetalles]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ListarLibrosDetalles]
AS
BEGIN
    SELECT 
        libros.id,
        libros.titulo,
        autores.nombre AS autor,
        generos.nombre AS genero,
        libros.anioPublicacion,
        libros.isbn,
        libros.descripcion
    FROM 
        libros
    INNER JOIN 
        autores ON libros.autorId = autores.id
    INNER JOIN 
        generos ON libros.generoId = generos.id;
END;
GO
/****** Object:  StoredProcedure [dbo].[SP_ActualizarLibro]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_ActualizarLibro]
    @id INT,
    @titulo VARCHAR(200),
    @autorId INT,
    @generoId INT,
    @anioPublicacion INT,
    @isbn VARCHAR(20),
    @descripcion VARCHAR(200),
    @estado BIT
AS
BEGIN
    UPDATE libros
    SET titulo = @titulo,
        autorId = @autorId,
        generoId = @generoId,
        anioPublicacion = @anioPublicacion,
        isbn = @isbn,
        descripcion = @descripcion,
        estado = @estado
    WHERE id = @id;
END;
GO
/****** Object:  StoredProcedure [dbo].[SP_BorrarLibro]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_BorrarLibro]
    @id INT
AS
BEGIN
    UPDATE libros
    SET estado = 0
    WHERE id = @id;
END;
GO
/****** Object:  StoredProcedure [dbo].[SP_BuscarLibroPorId]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_BuscarLibroPorId]
    @id INT
AS
BEGIN
    SELECT id, titulo, autorId, generoId, anioPublicacion, isbn, descripcion, estado
    FROM libros
    WHERE id = @id;
END;
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertarLibro]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_InsertarLibro]
	@id INT,
    @titulo VARCHAR(200),
    @autorId INT,
    @generoId INT,
    @anioPublicacion INT,
    @isbn VARCHAR(20),
    @descripcion VARCHAR(200)
AS
BEGIN
    INSERT INTO libros (id, titulo, autorId, generoId, anioPublicacion, isbn, descripcion, estado)
    VALUES (@id, @titulo, @autorId, @generoId, @anioPublicacion, @isbn, @descripcion,1);
END;
GO
/****** Object:  StoredProcedure [dbo].[SP_ListarLibros]    Script Date: 13/09/2024 18:48:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_ListarLibros]
AS
BEGIN
    SELECT id, titulo, autorId, generoId, anioPublicacion, isbn, descripcion
    FROM libros WHERE estado = 1;
END;
GO
