SET ANSI_NULLS ON
use WAPI_2025
GO
SET QUOTED_IDENTIFIER OFF
GO
/*
     Al crear una nueva factura, le generamos la seguiente secuencia  
     
   Fx Mod 30-03-2026 Se recibe nuevo parámetro @tipo_factura para ABONOS
                     Formato secuencia: AYYYY/00000001  
                     Se añaden los nuevos parámetros OUTPUT @fac_serie, @fac_number
   Fx Mod 30-12-2025 Nuevo formato de la secuencia yyyy/ssss (con barra)
   Fx Mod 17-12-2025 Nuevo formato de la secuencia yyyy-ssss (año-secuencia)
   Fx Mod 10-02-2010 (2.-DONLIMPIO) 
*/  
ALTER procedure [dbo].[sp_dameNuevoNumeroFactura]  
@cLetra varchar(1),
@tipo_factura int ,  
@cSecuencia varchar(14) ='' output  ,
@fac_serie varchar(5) output,
@fac_number int output
 as
BEGIN  
--declare @cLetra varchar(1),  
--@cSecuencia varchar(4)   
--set @cLetra = 'I'  
--declare @nuevoNumero int  
set @fac_serie  = case @tipo_factura when 3 then 'A' else '' end + convert(varchar(4), year(getDate()))

SELECT  @fac_number=isnull(max(fac_number)+1, 1)    
  FROM factura   
 WHERE right(left(id_obra, 5), 1) = @cLetra and fecha_baja IS NULL  
	   AND fac_serie =@fac_serie  
  

set @cSecuencia = right('00000000'+convert(varchar(4),@fac_number),8)  
  
if year(getDate()) > 2025
 set @cSecuencia = @fac_serie + '-' + @cSecuencia 
else  
 set @cSecuencia = @cSecuencia + '/' + @fac_serie  
END

