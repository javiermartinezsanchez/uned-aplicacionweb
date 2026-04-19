-- Modificación de la tabla de FACTURA con los dos nuevos campos.
alter table factura alter column secuencia [nvarchar](14) NOT NULL; 

alter table factura add fac_serie varchar(5) null, fac_number int null;
-- Generamos los valores de fac_serie y fac_number con los existentes en "secuencia".
update factura set fac_serie = 
case LEN(secuencia)
when 13 then LEFT(secuencia, 4)
else right(secuencia, 4)
end 
,
fac_number =
case LEN(secuencia)
when 13 then substring(secuencia, 6,10)
else left(secuencia, 4)
end 
where estado <> 0 and fecha_baja is null;
;
-- Revisión de las que no se hayan rellenado (deben de ser las que no están emitidas)
--select * from FACTURA where fac_serie is null;



