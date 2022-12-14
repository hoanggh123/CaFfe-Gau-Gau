USE [QuanCaPhe]
GO
/****** Object:  Table [dbo].[QLNV]    Script Date: 9/24/2022 7:21:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[QLNV](
	[maNV] [nchar](10) NOT NULL,
	[tenNV] [nchar](50) NOT NULL,
	[ngaySinh] [nchar](15) NOT NULL,
	[gioiTinh] [nchar](5) NOT NULL,
	[sdt] [nchar](11) NOT NULL,
	[diaChi] [nchar](30) NOT NULL,
	[taiKhoan] [nchar](15) NOT NULL,
	[matKhau] [nchar](20) NOT NULL
) ON [PRIMARY]
GO
