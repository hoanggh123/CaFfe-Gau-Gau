USE [QuanCaPhe]
GO
/****** Object:  Table [dbo].[DatBan]    Script Date: 9/24/2022 7:21:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DatBan](
	[tenKH] [nchar](50) NOT NULL,
	[sdt] [nchar](11) NOT NULL,
	[ban] [int] NOT NULL,
	[thoiGian] [nchar](10) NOT NULL,
	[ngay] [nchar](15) NOT NULL,
	[thanhToan] [nchar](20) NOT NULL,
	[ghiChu] [nchar](100) NOT NULL
) ON [PRIMARY]
GO
