@ECHO OFF


IF  "%1%" == "" (
  @ECHO  teclee nombre de fichero jar 
)  else  (
	REM  java --module-path  "C:\Java\javafx11\lib"  --add-modules=javafx.controls -jar %1
	java --module-path  %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml -jar %1
)

