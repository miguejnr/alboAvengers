# alboAvengers

Se tomaron varias consideraciones al realizar este proyecto, se decidió utilizar Spring para agilizar el proceso de desarrollo y configuración. Se usa MongoDB como base de datos dado que se estará manejando informacion desde un API y se espera como resultado un JSON que puede ser reutilizado en otras aplicaciones. El proyecto utiliza Maven para su construcción y manejo de dependencias.

El proyecto cuenta con 5 clases principales:

**AlboMarvelApplication**

Cuenta con la clase main que ejecuta la aplicación de Spring.

**MarvelController**

Gestiona el mapeo de la url y toma como variable el nombre del personaje del que se deseen obtener los datos, ya sea de colLaborators o de characters.

**GetCollaborators/GetCharacters**

Hacen un request a la base de datos y regresa los datos del personaje que fue mandado como parametro en la url. Si la última actualización de la base de datos no es la del día en curso se actualiza la información vía la clase UpdateInfo, si ya se encuentra actualizado únicamente muestra la información existente en la base de datos.

**UpdateInfo**

El backbone del backend, hace el HTTP request al API de Marvel y construye el string de JSON que sera actualizado en la base de datos y hace el correspondiente update en ella.

Cuenta también dentro de la raiz de proyecto con los archivos assemble.sh y avengers.sh, el primero limpia y construye la aplicaión y el segundo la ejecuta. Al ejecutarlos se pide autenticación de root debido a que Linux unicamente concede permisos de uso sobre el puerto 80 a este usuario.

Cualquier duda o comentario pueden hacerlo llegar a miguejnr@gmail.com
