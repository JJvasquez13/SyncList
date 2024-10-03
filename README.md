# SyncList
PROYECTO PERSONAL PARA DESARROLLAR EN APLICACIONES MÓVILES 1.

SyncList es una aplicación móvil que combina la gestión de listas de tareas, notas y recordatorios en una plataforma unificada, con la capacidad de compartir y colaborar en tiempo real. Los usuarios pueden crear listas para compras, tomar notas y configurar recordatorios, todo en una sola aplicación. Cada lista, tarea o nota puede ser compartida con contactos seleccionados, quienes podrán colaborar editando o completando elementos. La sincronización es automática, lo que garantiza que todos los cambios se actualicen en tiempo real para todos los usuarios involucrados.

FUNCIONALIDADES DE LA APLICACIÓN:
 - Creación de elementos unificados:
    Los usuarios pueden crear elementos (tareas, compras, notas o recordatorios) desde una sola interfaz. Al crear un nuevo elemento, puedes:
    Definir un título descriptivo (ej. "Comprar leche", "Idea para proyecto").
    Seleccionar la categoría (Compra, Nota, Recordatorio).
    Asignar un recordatorio (opcional) con una fecha y hora para recibir notificaciones.
    Compartir el elemento con otros usuarios, permitiéndoles editar o colaborar.

 - Gestión de listas:
    Todos los elementos (compras, notas y recordatorios) se muestran en una lista principal, donde se pueden ver las tareas activas o completadas.
    Los elementos están organizados por categorías y pueden ser filtrados para mostrar solo compras, notas o recordatorios.
    Los usuarios pueden marcar elementos como "completados", agregarlos o eliminarlos en cualquier momento.

 - Sincronización en tiempo real:
    Gracias a Firebase, los cambios que realicen los usuarios (como agregar un nuevo ítem o marcar un elemento como completado) se sincronizan instantáneamente en todos los dispositivos conectados, sin importar si la lista está compartida o es privada.
    Si un usuario realiza un cambio, todos los colaboradores reciben el cambio al instante en su lista.

 - Recordatorios y notificaciones:
    Los usuarios pueden asignar recordatorios a cualquier elemento de la lista (ya sea una compra, nota o tarea).
    Las notificaciones push recordarán a los usuarios sobre eventos importantes o tareas pendientes.
    Si una lista o tarea es compartida, todos los colaboradores recibirán el recordatorio cuando llegue el tiempo programado.

 - Colaboración en tiempo real:
    Los usuarios pueden compartir listas, notas o tareas con otras personas, seleccionando quién puede ver o editar los elementos.
    Los colaboradores pueden agregar, editar y eliminar elementos en la lista, con actualizaciones reflejadas en tiempo real para todos.
    Cada tarea compartida muestra quién realizó cada acción, lo que facilita la colaboración.

 - Modo offline:
    Los usuarios pueden trabajar sin conexión a internet, y los cambios realizados se sincronizarán automáticamente una vez que el dispositivo se vuelva a conectar.

¿CÓMO FUNCIONARÁ?
  - Inicio de sesión:
     Al iniciar la aplicación, los usuarios se registran o inician sesión usando su correo electrónico o autenticación mediante Google/Facebook (usando Firebase Authentication).

  - Pantalla principal:
     La pantalla principal muestra todas las listas activas y los elementos en ellas (compras, notas, recordatorios).
     Un botón flotante "+" permite crear un nuevo elemento, donde se puede definir si es una compra, nota o recordatorio.

  - Creación de un elemento:
     Al crear un nuevo elemento, el usuario puede darle un título, seleccionar la categoría (Compra, Nota o Recordatorio), añadir un recordatorio opcional, y seleccionar con quién compartirlo.
     Todos los elementos aparecen en una sola lista, con íconos o colores que identifican si es una compra, una nota o un recordatorio.

  - Edición y colaboración:
     Los usuarios pueden compartir listas con otros contactos para colaborar en tiempo real. Cualquier usuario con acceso puede editar, eliminar o agregar nuevos elementos a la lista compartida.
     Los cambios son visibles de inmediato para todos los usuarios conectados a la lista.

  - Recordatorios y notificaciones:
     Los usuarios pueden agregar recordatorios a las tareas o notas y recibir notificaciones push cuando la fecha y hora del recordatorio se cumpla.
     Si una tarea es compartida, todos los usuarios conectados recibirán la notificación.

  - Sincronización y cambios en tiempo real:
     Si un usuario realiza un cambio en una lista (por ejemplo, marca un elemento como completado), el cambio se sincroniza automáticamente para todos los usuarios que comparten esa lista.
     Los usuarios pueden ver quién realizó el cambio más reciente, facilitando la coordinación en grupo.

EJEMPLO PRÁCTICO:
- Crear una lista de compras:
   Juan crea una lista de compras y agrega productos como "Pan", "Leche" y "Huevos". Luego comparte esta lista con Dorian.
   Dorian agrega "Queso" a la lista, y Juan lo verá automáticamente en su dispositivo. Si Dorian marca "Leche" como completada, Juan también verá este cambio en tiempo real.
        
- Nota compartida con recordatorio:
   Ana crea una nota con el título "Idea para proyecto" y añade un recordatorio para revisarla el viernes a las 10:00 AM.
   Comparte esta nota con su colega Pedro. Cuando llegue el viernes, ambos recibirán una notificación recordándoles revisar la idea.

Con SyncList, los usuarios tendrán una herramienta completa para gestionar sus tareas diarias, ya sea para organizar sus compras, tomar notas rápidas o configurar recordatorios, con la capacidad adicional de compartir y colaborar en tiempo real con otras personas.
