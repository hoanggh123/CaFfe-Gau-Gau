USE [QuanCaPhe]
GO
/****** Object:  Table [dbo].[ThongKe]    Script Date: 9/24/2022 7:21:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ThongKe](
	[ban] [int] NOT NULL,
	[tongTien] [nvarchar](50) NOT NULL,
	[tienKH] [nvarchar](50) NOT NULL,
	[tienThua] [nvarchar](50) NOT NULL,
	[tenNV] [nvarchar](50) NOT NULL,
	[ngay] [nvarchar](30) NOT NULL,
	[thoiGian] [nvarchar](20) NOT NULL
) ON [PRIMARY]
GO
