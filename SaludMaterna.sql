CREATE DATABASE SaludMaterna;
USE SaludMaterna;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE usuaria (
    id_User INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    domicilio TEXT NOT NULL,
    curp varchar(18) not null,
    Num_celular VARCHAR(20) NOT NULL,
    Num_emergencia VARCHAR(20) NOT NULL,
    talla DECIMAL(3,2) NOT NULL,
    peso DECIMAL(5,2) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,  
    password_hash VARCHAR(255) NOT NULL, 
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE last_mestruacion(
    id_last INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_User INT UNSIGNED NOT NULL,
    ult_mest DATE NOT NULL,
    FOREIGN KEY (id_User) REFERENCES usuaria(id_User)
);


create table ConsultaPrenatales(
	id_Prenatal int UNSIGNED auto_increment Primary Key,
    fecha_consulta DATE NOT NULL,
    id_last int UNSIGNED not null, 
    foreign key (id_last) references last_mestruacion(id_last)
);


create table Ultrasonido(
	id_Ultrasonido int UNSIGNED auto_increment Primary Key,
    cita_ultrasonido DATE NOT NULL,
    id_last int UNSIGNED not null, 
    foreign key (id_last) references last_mestruacion(id_last)
);

CREATE TABLE chatbot_preguntas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    categoria ENUM('EMBARAZO', 'PUERPERIO', 'GENERAL') NOT NULL,
    pregunta_patron VARCHAR(500) NOT NULL,
    respuesta TEXT NOT NULL,
    palabras_clave VARCHAR(1000),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) #ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;

INSERT INTO chatbot_preguntas (categoria, pregunta_patron, respuesta, palabras_clave) VALUES
('EMBARAZO', 'alimentación durante el embarazo', 
 'Durante el embarazo es importante llevar una dieta equilibrada con frutas, verduras, proteínas y ácido fólico. Se recomienda consumir alimentos ricos en hierro, calcio y ácido fólico para el desarrollo del bebé.',
 '["alimentacion", "comida", "dieta", "nutricion"]'),

('EMBARAZO', 'ejercicios para embarazadas', 
 'El ejercicio moderado como caminar, yoga prenatal o natación son recomendables durante el embarazo. Siempre consulta con tu médico antes de comenzar cualquier rutina de ejercicios.',
 'ejercicio,deporte,actividad,caminar,yoga'),

('PUERPERIO', 'cuidados después del parto', 
 'Durante el puerperio es importante descansar, mantener una buena alimentación y cuidar la zona del periné. No dudes en pedir ayuda a tu pareja, familiares o amigos.',
 'cuidados,recuperacion,postparto,descanso'),

('PUERPERIO', 'lactancia materna', 
 'La lactancia materna requiere paciencia y posición correcta. Busca apoyo de especialistas si tienes dificultades. La lactancia proporciona anticuerpos y nutrientes esenciales para tu bebé.',
 'lactancia,amamantar,leche,pecho'),

('GENERAL', 'saludo', 
 '¡Hola! Me alegra que estés aquí. ¿En qué puedo ayudarte hoy?',
 'hola,buenos dias,buenas tardes');
 
 INSERT INTO chatbot_preguntas (categoria, pregunta_patron, respuesta, palabras_clave) VALUES
('EMBARAZO', '¿En qué semana se alcanza a ver el embrión en el ultrasonido, o el saco gestacional?',
 'Es importante recordar que en el embarazo, existen muchas estructuras que se forman que ayudan a la protección y crecimiento del bebé. Por eso en las primeras semanas del embarazo, no se logran ver de manera adecuada por el ultrasonido, hasta que alcanzan un tamaño que pueda ser detectado por este medio. En general, el saco gestacional puede verse a partir de la semana 4 o 5 del embarazo, mientras que el embrión (o bebé) puede ser visible en el saco gestacional aproximadamente entre la semana 6 o 7.',
 'alimentacion,comida,dieta,nutricion'),

('EMBARAZO', '¿En las primeras semanas siento dolor parecido a un cólico ligero en el vientre, es normal?',
 'Sí. En realidad es muy común sentir un dolor cólico leve o ligero en el vientre durante las primeras semanas del embarazo, que muchas veces se siente como pequeños pinchazos o punzadas en la parte baja de tu vientre. Frecuentemente este dolor se debe a los cambios que se están generando a nivel del útero. Pero es importante que prestes atención a esos cambios, en particular si el dolor se incrementa, o bien, tienes sangrado, dolor al orinar o fiebre. En estos casos es mejor que acudas a tu médico de inmediato para que verifiquen que todo se encuentre bien.',
 '["dolor", "cólico", "pinchazos", "vientre", "normal", "primeras semanas", "molestia", "punzadas", "útero", "cambios"]'),

('EMBARAZO', '¿Cuántos latidos son los normales en mi bebé?',
 'Esto va a depender de las semanas de gestación, sin embargo el rango esperado es de 110 a 160 latidos por minuto. Es rápido, ¿no? Esto ocurre porque a pesar de su tamaño, el corazón del bebé en esta etapa del embarazo necesita bombear rápidamente la sangre a todos los tejidos que están en desarrollo y crecimiento en su cuerpo. Esto garantiza que lleguen los nutrientes y oxígeno suficiente a sus células que están teniendo una gran transformación para formar y hacer madurar a su cuerpo, así como que la sangre se lleve las sustancias de desecho para depositarlas en la sangre de su mamá y que puedan eliminarse así de sus organismos. Cuando el latido cardiaco del bebé se encuentre fuera de esos rangos (menos de 110 o más de 160 latidos por minuto) es necesario acudir a un servicio de urgencias de obstetricia para verificar cómo se encuentra.',
 '["latidos", "corazón", "frecuencia cardiaca", "normal", "bebé", "taquicardia", "bradicardia", "ritmo cardiaco", "corazón fetal", "monitoreo"]'),

('EMBARAZO', '¿Qué cuidados son los principales durante todo el embarazo?',
 'Me alegra que hagas esa pregunta. Significa que eres una mamá que toma en serio su salud y la de tu bebé. Es muy importante reconocer que el embarazo es una etapa normal y no debe verse como una enfermedad, pero que le pide a tu cuerpo mayor trabajo (recuerda que tu cuerpo no solo debe responder por ti, sino por el bebé). Por eso es muy importante que durante todo el embarazo cuidemos los siguientes puntos:

Alimentación: Una alimentación balanceada será la clave para un embarazo sano y una vida saludable para ti y tu bebé. Cuando tú te alimentas sanamente, no solo se fortalece tu cuerpo, sino que le estás enseñando al cuerpo de tu bebé cómo funcionar y fortalecerse. Por eso debes vigilar comer suficientes verduras, fibra y proteínas, y modular la cantidad de carbohidratos (azúcares, pastas, dulces, panes y frutas) y grasas que consumes. Idealmente debes evitar los embutidos, huevos crudos y lácteos no pasteurizados. De igual modo, es importante que vigiles tu hidratación. Lo recomendable es consumir 2 litros de agua al día (esto puede variar en los meses de más calor, puedes necesitar un poco más), pero recuerda que en esos 2 litros están incluidos los líquidos de los alimentos como sopas o caldos. Si tienes algún problema de salud como diabetes, hipertensión o enfermedad renal, es importante que consultes con tu médico y nutriólogo los ajustes que debas hacer a tu alimentación en las diferentes etapas del embarazo. También es importante evitar consumir bebidas alcohólicas, porque esto puede afectar gravemente el desarrollo y maduración de tu bebé.

Actividad física: Llevar a cabo un ejercicio ligero a moderado durante el embarazo es importante para fortalecer tu cuerpo. Cuando hacemos ejercicio, nuestros músculos se activan y esto nos ayuda a tener un mejor metabolismo y a producir sustancias que nos dan satisfacción y bienestar. Esto mismo le ocurrirá a tu bebé. Por eso es importante realizar actividad física leve o moderada a lo largo del embarazo, como caminar o nadar. Lo recomendable es tomar una caminata ligera de 30 minutos al menos 3 o 4 veces por semana (aunque idealmente puedes hacerlo todos los días). Pero cuidado, recuerda que el embarazo es un esfuerzo constante de tu cuerpo, por lo que debes evitar esfuerzos excesivos y equilibrar la actividad física que realices con periodos de descanso. Recuerda que principalmente en las primeras etapas del embarazo, puedes llegar a sentirte más cansada o con mucho sueño, así que tomar una siesta ligera de 15 o 20 minutos en el día, puede ser una buena opción.

Control prenatal: Es importante acudir a tu médico en las diferentes etapas del embarazo, para verificar que las cosas vayan bien tanto contigo, como con tu bebé. Recuerda que es una etapa normal, pero que existen algunas complicaciones que debemos evitar y la mejor manera de hacerlo es a través de las revisiones que te haga tu médico para detectar algún cambio que indique que debes tomar algún cuidado adicional.

Evita el consumo de sustancias: Recuerda que el cuerpo de tu bebé está en construcción y maduración durante el embarazo, por eso es más susceptible a las diferentes sustancias que ingresan en tu cuerpo. Más aún, es importante reconocer que la placenta, que es el órgano que se encarga de proteger el embarazo, de nutrir al bebé y el que decide en qué momento nace el bebé y que este órgano también es susceptible a las acciones tóxicas de muchas sustancias que ingresan a tu organismo, como los químicos que ingresan con el humo del cigarro, la cocaína, la heroína, la marihuana, etc. Estas sustancias no solo pueden traer problemas en tu bebé, sino causarte complicaciones durante el embarazo. Por eso, lo mejor es evitarlas.

Salud mental: Es fundamental cuidar tu bienestar emocional durante el embarazo.

Vacunas: Es importante aplicarse las vacunas recomendadas como la de Influenza (según temporada) y la TDPA (Tétanos, difteria y tos ferina) a partir de la semana 20.',
 'cuidados, consejos, alimentación, ejercicio, control prenatal, vitaminas, qué evitar, actividad fisica, hidratacion, sustancias tóxicas, alcohol, tabaco, drogas, vacunas, salud mental, prevención'),

('EMBARAZO', '¿Tener mucho vómito es normal? ¿Qué puedo hacer para calmarlo?',
 'Como parte de los síntomas del embarazo, de los más frecuentes son las náuseas y el vómito. Esto puede ser percibido por cada mujer de manera diferente. Para algunas personas es una sensación casi imperceptible, mientras que para otras se vuelve un verdadero problema. Es normal sentir una sensación de náuseas o incluso vomitar, principalmente en la mañana y en los primeros meses del embarazo, pero poco a poco esta sensación va disminuyendo y en las siguientes etapas dejan de estar presentes. Una solución simple en ocasiones consiste en comer un poco de hielo, o incluso en comer una galleta pequeña cuando la sensación de náuseas está presente. Sin embargo, si tú sientes que estas sensaciones de náuseas o los vómitos son muy intensos, al grado de que no te permiten ingerir alimentos, ni líquidos, o que incluso han hecho que pierdas peso, debes acudir a tu médico lo más pronto posible, para verificar tu estado de salud y que te pueda ayudar con este problema.',
 'vómito, náuseas, mareo, matutino, controlar, remedio, hiperémesis, alivio, primer trimestre, malestar'),

('EMBARAZO', '¿Debo tomar vitaminas, ácido fólico, hierro o todas a la vez? ¿Durante cuánto tiempo?',
 'Me parece excelente tu pregunta. Muchas veces relacionamos las vitaminas o algunos suplementos con problemas de nutrición o pensamos que pueden "engordarnos". Pero es importante recordar que nuestro embarazo es una etapa en la que tu cuerpo se exige más para mantenerte sana a ti y a tu bebé. Por eso necesita una ayuda extra. En la mayoría de las ocasiones esto no se logra sólo con la nutrición durante el embarazo. Por eso es importante que le ayudemos un poco tomando algunos suplementos nutricionales como las vitaminas, el ácido fólico, el complejo B y el hierro. A continuación te explico la función de cada una y el tiempo en el que debes consumirlo:

Hierro: Durante el embarazo tu cuerpo produce más hemoglobina que es la proteína que está en tu sangre que se encarga del transporte de oxígeno. Esta hemoglobina utiliza el hierro para poder cargar al oxígeno desde los pulmones hasta los tejidos (los tuyos y los de tu bebé). Así, durante el embarazo los aportes que consumas de hierro serán fundamentales para garantizar el buen transporte de oxígeno por todo tu cuerpo y el de tu bebé, evitando que caigas en anemia. Aún más, este nutriente es importantísimo a lo largo del embarazo para la maduración del cerebro de tu bebé. Cuando una mujer sufre de anemia en el embarazo o no tiene el suficiente aporte de hierro, se le cae más el cabello, puede tener un parto prematuro, el bebé nacer de bajo peso o incluso tener un desarrollo postnatal alterado. Por eso tu médico te recomendará un suplemento de hierro desde el primer trimestre del embarazo y durante toda tu gestación. Inclusive es posible que te recomiende tomarlo durante los primeros meses después del nacimiento de tu bebé para garantizar una lactancia saludable. Lo más recomendable es tomarlo una vez al día en ayunas o entre las comidas y evitar consumirlo con alimentos como la leche, porque esta puede disminuir la cantidad de hierro que absorbe nuestro cuerpo. Puede tomarse en combinación con el ácido fólico.

Vitaminas: Son elementos esenciales que facilitan muchos de los procesos que se desarrollan en tu cuerpo y en el del bebé. Por eso es importante consumirlos durante el embarazo, para que estos procesos se lleven a cabo de manera adecuada y sin complicaciones. Existen muchos suplementos vitamínicos especiales para la etapa del embarazo que están disponibles. Tu médico te recomendará el más apto para tu caso, pero en general verás que contienen elementos como Calcio (ayuda al buen funcionamiento de la placenta), Yodo (permite un adecuado funcionamiento de la tiroides, que es una glándula que te ayuda a regular tu metabolismo, y el de tu bebé, incluso la maduración de su cerebro), Omegas o DHA (son ácidos grasos fundamentales para el desarrollo cerebral y del ojo del bebé. También ayuda a modular levemente los cambios emocionales durante el embarazo). Es importante consumir estos nutrientes a lo largo del embarazo, y en ocasiones durante la etapa de lactancia.

Ácido fólico y complejo B: En realidad estas también son vitaminas. El ácido fólico es la vitamina B9 y el complejo B contiene vitaminas B6 y B12. El ácido fólico nos ayuda al mejor funcionamiento de nuestras células regulando la expresión de nuestros genes. Así es, modula los genes que deben estar en escena para que nuestros procesos biológicos se cumplan de manera adecuada. Aunque es fácil encontrar esta vitamina en la naturaleza (vegetales de hojas verdes como espinacas, quelites, arúgula, etc.), existen algunas variaciones en la forma en que las personas pueden aprovechar esta vitamina que hace necesario que utilicemos un suplemento alimenticio para garantizar que los niveles de ácido fólico en nuestro cuerpo, son los adecuados. En México, más de la mitad de las personas tienen esta variación, por eso es muy importante que lo consumamos, principalmente en el embarazo. Si bien, el consumo regular de ácido fólico nos ayuda a mantener nuestros procesos metabólicos saludables, durante la etapa del embarazo, esto es más importante porque para el bebé que está en formación una deficiencia de esta vitamina, puede producir un problema grave en su formación como la anencefalia (no se forma su cerebro) o el mielomeningocele (parte de su médula espinal sale a través de su columna). Por eso escucharás que es importante que consumas el ácido fólico principalmente los primeros meses del embarazo (idealmente incluso 3 meses antes del embarazo y hasta la lactancia). Por otra parte, la vitamina B12 es un cofactor del ácido fólico, o sea, es un ayudante del ácido fólico para que se potencie y logre su objetivo de regular nuestros procesos metabólicos. Por eso es que cuando tomamos ácido fólico, también se recomienda tomar complejo B. Pero cuidado. Como son vitaminas parecidas, compiten por su absorción en nuestro cuerpo. Por ello, deberíamos tomar el ácido fólico en un momento diferente del día al que tomamos el complejo B.',
 'vitaminas, suplementos, ácido fólico, hierro, complejo B, cuánto tiempo, importancia, anemia, calcio, yodo, DHA, omega, dosificación, horario, absorción'),

('EMBARAZO', '¿Cuáles son las señales de emergencia durante mi embarazo?',
 'En el embarazo algunas molestias o síntomas que te indican que debes acudir inmediatamente a urgencias de obstetricia son:

Dolor de cabeza intenso: Si bien en ocasiones podemos tener dolor de cabeza, durante el embarazo estos dolores pueden ser una señal de alarma que indica que tu presión arterial puede estar subiendo y recuerda que la presión alta en el embarazo puede ser en extremo peligrosa para ti y tu bebé. Por eso es importante que si tienes dolor de cabeza intenso, acudas inmediatamente al servicio de urgencias obstetricia que esté más cercano a tu ubicación, no importa si tienes o no seguridad social, en cualquier hospital público te podrán revisar.

Zumbido de oídos y ver lucecitas: No es normal que te zumben los oídos o que de pronto veas lucecitas. Estos dos síntomas pueden estar indicando que tu presión está subiendo súbitamente a niveles peligrosos. Lo mejor que puedes hacer, si tienes alguno de estos síntomas es acudir inmediatamente al servicio de urgencias que esté más cerca de ti para que revisen tu presión y vean si requieres un manejo inmediato.

Sangrado: No es normal que haya salida de sangre a través de tu vagina. Cuando tienes sangrado durante el embarazo, las cosas no están bien. Puede tratarse de una lesión, un desprendimiento de la placenta o incluso una amenaza de aborto. Sea cual sea el motivo, puede representar un riesgo importante para ti y tu bebé, por lo que si presentas sangrado, debes acudir inmediatamente al servicio de urgencias que se encuentre más cerca de ti.

Dolor en la boca del estómago: Es conocido como epigastralgia, es un dolor agudo en la parte alta del estómago, justo por debajo del esternón en la parte media del cuerpo (comúnmente conocido como boca del estómago). Esta zona sensible puede indicarnos diferentes problemas, desde una gastritis, hasta un incremento súbito de la presión arterial con inflamación de las vísceras que requiere atención inmediata. Por eso, si tienes dolor en la boca del estómago, no lo dudes y acude a tu médico inmediatamente, en particular si ese dolor está acompañado de dolor de cabeza, zumbido de oídos, ver lucecitas o presión alta.

Salida de líquido por la vagina: Es importante saber que por los cambios hormonales del embarazo, es frecuente que tengas un poco de flujo. Pero la consistencia del flujo es un poco más parecida al moco, es decir, es viscosa. Sin embargo, si en vez de ser un fluido viscoso, es más líquido, como agua o pipí a través de tu vagina, es un indicador de que algo no anda bien. Puede tratarse de una ruptura de la bolsita en la que está desarrollándose el bebé. Por eso, si tienes salida de "líquido" (como agua o pipí) a través de la vagina, debes acudir inmediatamente con tu médico para una valoración.

Contracciones intensas: Es normal que sientas algunas molestias como el dolor tipo cólico leve, principalmente en las últimas etapas del embarazo. Pero el sentir contracciones del útero intensas, dolorosas, que no disminuyan con el reposo, es una señal de alarma. Cuando tenemos contracciones intensas nuestro útero se comienza a presionar y esto puede generar la expulsión del bebé. Si esto ocurre antes de tiempo, el bebé no puede estar lo suficientemente desarrollado para el nacimiento y tener complicaciones graves. Por eso, si sientes estas contracciones debes acudir inmediatamente al servicio de urgencias obstétricas más cercano.

Convulsiones: Las convulsiones son movimientos repentinos, involuntarios e incontrolables que pueden acompañarse con pérdida del sentido o conocimiento. Habitualmente ocurren en personas que padecen epilepsia, pero durante el embarazo, pueden ser un problema que se presenta por tener una presión arterial tan alta, que afecta al cerebro generando la crisis convulsiva. Por eso, si llegas a tener una convulsión, no importa si antes del embarazo tenías convulsiones o no, debes acudir inmediatamente al servicio de urgencias obstétricas más cercano, sin perder tiempo, porque tu vida puede estar en peligro.

Presión alta: El aumento en los valores de presión arterial en el embarazo, puede ser una de las señales de alarma más frecuentes. Cuando estamos embarazadas y la presión aumenta, no solo se generan los daños que ocurren en las personas con hipertensión, sino que nuestro cuerpo es más susceptible para sufrir cambios que nos afecten gravemente, principalmente nuestro hígado, riñones y cerebro. Si tienes unas cifras de presión arterial mayores a 140/90 debes acudir a tu médico inmediatamente para una valoración, en particular si se acompaña de zumbido de oídos, dolor de cabeza o que veas lucecitas. Aún más, si tu presión es mayor a 160/110, no pierdas tiempo y acude inmediatamente a al servicio de urgencias obstetricia más cercano (no importa si eres derechohabiente o no), debes atenderte inmediatamente porque en este caso tu vida corre mucho riesgo.',
 'señales alarma, emergencia, urgencias, sangrado, dolor cabeza, presión alta, líquido vaginal, contracciones, convulsiones, zumbido oídos, lucecitas, epigastralgia, dolor estómago, ruptura membranas, preeclampsia, hipertensión'),

('EMBARAZO', '¿Puedo hacer ejercicio?',
 'Qué bueno que preguntas eso. La respuesta es sí, pero con moderación. Llevar a cabo un ejercicio ligero a moderado durante el embarazo es importante para fortalecer tu cuerpo. Cuando hacemos ejercicio, nuestros músculos se activan y esto nos ayuda a tener un mejor metabolismo y a producir sustancias que nos dan satisfacción y bienestar. Esto mismo le ocurrirá a tu bebé. Por eso es importante realizar actividad física leve o moderada a lo largo del embarazo, como caminar o nadar. Lo recomendable es tomar una caminata ligera de 30 minutos al menos 3 o 4 veces por semana (aunque idealmente puedes hacerlo todos los días). Pero cuidado, recuerda que el embarazo es un esfuerzo constante de tu cuerpo, por lo que debes evitar esfuerzos excesivos y equilibrar la actividad física que realices con periodos de descanso. Recuerda que principalmente en las primeras etapas del embarazo, puedes llegar a sentirte más cansada o con mucho sueño, así que tomar una siesta ligera de 15 o 20 minutos en el día, puede ser una buena opción.',
 'ejercicio, actividad física, permitido, caminar, nadar, intensidad, recomendaciones, deporte, moderación, descanso'),

('EMBARAZO', '¿Hay riesgo si tengo relaciones sexuales durante el embarazo?',
 'La sexualidad es un aspecto importante en nuestra vida y salud. El hecho de estar embarazada no anula los demás aspectos de tu vida y tanto tú como tu pareja tienen derecho a disfrutar plenamente de su sexualidad de manera sana en todas las etapas. Es importante saber que el semen cuenta con algunas sustancias que pueden estimular al útero para contraerse, lo que puede ser peligroso en el embarazo, por eso se recomienda utilizar un preservativo para prevenir esto, además de que por la condición del embarazo eres más vulnerable para tener una infección. En esto último también te ayudará el preservativo. También deben cuidar la comunicación y mantenerse atentos a los cambios de tu cuerpo, para que se sientan cómodos. Es normal que durante tu embarazo experimentes cambios en la líbido (deseo sexual) por los propios cambios hormonales que estás experimentando. No tengas miedo de comentarlo con tu pareja y con tu médico. Lo más importante es buscar espacios y posiciones que no presionen tu abdomen o con los que tengan riesgo de caer, sobretodo en las etapas más avanzadas. Pero cuidado, si tienes sangrado, dolor al momento de tener relaciones, salida de líquido amniótico, deberían esperar a tener relaciones sexuales, después de que su médico verifique que no hay riesgos.',
 'relaciones sexuales, sexo, riesgo, preservativo, recomendaciones, posiciones, seguridad, deseo sexual, líbido, infecciones, semen, contracciones'),

('EMBARAZO', '¿Es normal si mi flujo es más espeso?',
 'Debido a los cambios hormonales, tu flujo vaginal puede cambiar a lo largo del embarazo. Incluso puede pasar de ser como un moco transparente a tener una consistencia ligeramente gelatinosa al final del embarazo. Pero siempre va a ser ligeramente transparente y sin olor. Si a lo largo del embarazo tuvieras flujo verdoso, amarillo, blanco y grumoso (como requesón o leche cortada), con mal olor o que te cause comezón o ardor, debes acudir a tu médico, porque puede tratarse de una infección vaginal. El riesgo de dejar estas infecciones sin tratar, es que aumente la infección y llegue hasta la bolsa donde se encuentra creciendo el bebé, lo que precipitaría el trabajo de parto, poniéndote en riesgo a ti y al bebé.',
 'flujo, vaginal, espeso, normal, infección, olor, candidiasis, cambios hormonales, consistencia, transparente, gelatinoso, comezón, ardor'),

('EMBARAZO', '¿Con qué periodicidad debo ir a consulta o hacerme ultrasonidos?',
 'Las consultas de control prenatal y los ultrasonidos son muy importantes para vigilar que las cosas vayan bien contigo y tu bebé durante el embarazo. Por eso debes acudir a consulta médica al menos 5 veces durante el embarazo. La primera consulta debe ser durante las primeras 12 semanas del embarazo, lo ideal es que en cuanto te sepas embarazada, agendes una cita con tu médico para revisión. La segunda consulta se debe realizar entre la semana 22 y la semana 24 del embarazo, la tercera alrededor de la semana 27, la cuarta en la semana 31 y la quinta en la semana 35. Adicionalmente puedes tener otras consultas después de la semana 36 (habitualmente cada 2 semanas), hasta el final del embarazo. En caso de que tengas alguna condición de salud, o tu médico encuentre algún factor de riesgo en el embarazo que sea necesario vigilar, puede solicitarte que acudas a consultas adicionales.

Respecto a los ultrasonidos, estos estudios se enfocan en valorar al bebé y algunos datos de la placenta y tu circulación, que le darán información valiosa a tu médico para vigilar tu embarazo. El primer ultrasonido se recomienda realizarse entre la semana 11 y la 13 del embarazo, en este se van a valorar datos precisos de tu bebé y la presión de tus arterias uterinas, con lo que se podrán reconocer riesgos de algún defecto de nacimiento de tu bebé o riesgos de preeclampsia para ti. Es posible que antes de estas semanas, tú médico ya te haya practicado algún ultrasonido en su consultorio para ver el crecimiento del saco gestacional o el latido de tu bebé, pero en realidad a estos ultrasonidos les llamamos rastreos, porque no son tan detallados como el de la semana 11 a 13, que también se conoce como ultrasonido diagnóstico o de segundo nivel. El segundo ultrasonido se realiza entre la semana 18 y 22 del embarazo y se conoce como ultrasonido estructural. Es considerado como la primer consulta del bebé porque como su nombre lo dice, revisa a detalle la estructura de todo el cuerpo del bebé, desde su cerebro, hasta los huesos de sus manos y pies. Mide también la cantidad de líquido amniótico y el grado de maduración de la placenta. El tercer ultrasonido se realiza entre la semana 29 y 30 del embarazo, y se enfoca a valorar el crecimiento del bebé. En este ultrasonido se tomarán mediciones del cuerpo del bebé y se comparará con las mediciones que se hicieron en los ultrasonidos previos. El objetivo de esto es verificar que el bebé esté creciendo al ritmo adecuado, que no esté quedándose chiquito o desproporcionado, o por el contrario, que esté más grande de lo que debería (macrosómico). También se vigilará la cantidad de líquido amniótico y el grado de madurez de la placenta que esté acorde a lo esperado para ese momento de la gestación.',
 'consultas, control prenatal, frecuencia, ultrasonidos, ecografías, cuándo, programa, citas médicas, ultrasonido diagnóstico, ultrasonido estructural, ultrasonido crecimiento, semanas, seguimiento'),

('EMBARAZO', '¿Qué estudios son los indicados para darle seguimiento a mi embarazo y ver que todo vaya en orden?',
 'A lo largo de tu embarazo el médico solicitará diferentes estudios, pero no te sientas abrumada, es parte del control prenatal normal que debe hacer para verificar que todo vaya bien. Dependiendo de la etapa del embarazo podrá solicitarte diferentes estudios de laboratorio para ir descartando riesgos y así asegurar que vas transitando por un embarazo saludable. Estos estudios son:

Perfil prenatal. Estos estudios se realizan antes de la semana 13 para verificar que tu estado de salud al principio del embarazo, se encuentre bien. Estos estudios son:
- Biometría hemática completa. Es un estudio de sangre en el que analizan las células que están en tu torrente sanguíneo. Ayudará a tu médico para ver tu nivel de hemoglobina y con eso detectar si tienes o no anemia o si debe ajustar la dosis que te recomienda de hierro. También ayuda a descartar la presencia de infecciones en tu cuerpo, que tengan que tratarse.
- Grupo sanguíneo. Este es un estudio básico que solicitará tu médico para comprobar tu tipo de sangre. Esto tiene dos objetivos, el primero es estar preparado en caso de una emergencia, si tú requirieras una transfusión al momento del nacimiento de tu bebé, el saber con antelación tu tipo de sangre puede ser vital. El segundo verificar que no existan incompatibilidad en los grupos de sangre entre tu bebé y tú. En general, cuando la mamá tiene un tipo de sangre negativo, es necesario tener precaución al momento del nacimiento del bebé, para evitar que si hubiera mezcla de la sangre de mamá y bebé, hubiera alguna reacción no deseada como una anemia hemolítica en el recién nacido, si este tuviera un tipo de sangre positivo.
- Examen general de orina y urocultivo. Estos son estudios de orina que te solicitará tu médico para descartar una posible infección. Las infecciones de vías urinarias en el embarazo pueden ser más molestas, pero sobretodo poner en riesgo al embarazo. Incluso pueden precipitar el trabajo de parto o conducir a la muerte del bebé o una sepsis en la mamá. Por ello tu médico te solicitará estos estudios para detectar de manera oportuna estas infecciones e iniciarte tratamiento en caso necesario. Principalmente porque existe la posibilidad de que tú tengas una infección de vías urinarias y no tengas ninguna molestia.
- VDRL y VIH. Las infecciones de transmisión sexual son más comunes de lo que crees. Una gran proporción de la población que tiene alguna de estas infecciones ni siquiera se da cuenta, lo que hace aún más difícil su control. En el caso del embarazo, el sistema inmunológico de la mamá se encuentra vulnerable, por lo que es más susceptible a sufrir una infección. Es por ello, que aunque tú solo tengas relaciones con tu pareja, es importante realizar estos estudios de detección de infecciones de transmisión sexual. Es parte de la rutina que se sigue para verificar que las cosas vayan bien. No debes sentirte preocupada porque tú médico haya solicitado estos estudios. Al contrario, debes sentirte apapachada y con la seguridad de que se están haciendo las cosas que se deben hacer. Si tu resultado fuera positivo, tu médico te explicará el manejo más adecuado para tu caso y las opciones que hay disponibles para evitar riesgos en el nacimiento de tu bebé. Actualmente existe una serie de pasos que se pueden seguir para evitar problemas en el bebé y contigo.
- Glucosa en ayunas y Hemoglobina glicosilada. Algunas hormonas que se generan el embarazo pueden afectar la manera en que nuestro cuerpo regula el azúcar (glucosa). Por esta razón es importante que antes de las 15 semanas del embarazo, tu médico comience a revisar cómo se encuentra tu metabolismo de los azúcares por lo que, en caso de que tú o alguno de tus familiares tengan obesidad o diabetes, tu médico solicitará que te realices un estudio de glucosa en ayunas y hemoglobina glicosilada, con lo que podrá verificar tu control metabólico de glucosa y descartar diabetes gestacional.

Tamizajes de segundo trimestre. Estos estudios están enfocados en descartar problemas de salud que pueden estar presentes en el embarazo y por el embarazo como la diabetes gestacional, infecciones o anemia entre la semana 14 a 28. Por ello se realizan estos estudios:
- Tamiz de glucosa (Glucosa en ayunas y Curva de tolerancia a la glucosa). Debido a las hormonas del embarazo, en cualquier mujer embarazada se debe descartar la presencia de diabetes gestacional, principalmente entre la semana 24 y 28. Esto se realiza a través de una prueba de curva de tolerancia a la glucosa oral en la que el personal de laboratorio te pedirá ingerir una bebida con 75 gr de glucosa y te tomará una nueva muestra de sangre 2 horas después. Esto permitirá verificar cómo tu organismo trabaja con los azúcares que consumes. Es importante decir, que si tú ya cuentas con un diagnóstico de diabetes, no deben realizarte esta prueba.
- Biometría hemática. Al igual que en el primer trimestre, este estudio ayudará a tu médico a descartar la presencia de anemia, verificar si debe o no realizar algún ajuste en tu dosis de hierro, o si está presente alguna infección.
- Examen general de orina. Al igual que en el primer trimestre, este estudio ayudará a tu médico a descartar una infección de vías urinarias que no hayas detectado.

Tercer trimestre. Verificación. En este periodo, los estudios están encaminados a verificar que las cosas vayan bien para el momento del nacimiento. Se deben realizar entre las 28 y 40 semanas de gestación:
- Biometría hemática. Al igual que en los trimestres anteriores, este estudio ayudará a tu médico a descartar anemia e infecciones. Adicionalmente, en caso de que hayas cursado con presión alta en el embarazo, le permitirá evaluar el volumen de tus plaquetas, que pueden estar alteradas en los casos de preeclampsia.
- VDRL y VIH. Al igual que en el primer trimestre, estos estudios se realizan para descartar infecciones de transmisión sexual en el embarazo, debido a la mayor susceptibilidad de las mujeres embarazadas para adquirir estas infecciones y a que en muchos casos pueden ser asintomáticas. Tu médico determinará si es necesario realizar este estudio en tu caso, como parte del protocolo de seguimiento del embarazo.
- Examen general de orina y urocultivo. En el tercer trimestre existe un mayor riesgo de tener una infección de vías urinarias debido a la presión del útero sobre la vejiga. Por eso, es preciso realizar estos estudios, a fin de detectar este problema y comenzar un tratamiento oportuno. De no ser así, se tendrían riesgos de un parto pretérmino o precipitado, infección en el bebé o sepsis.

Existen otros estudios adicionales que tu médico podría recomendar dependiendo de cada caso. Sin embargo, siéntete con la confianza de preguntar a tu médico si necesitas información adicional o tienes dudas al respecto.',
 'estudios, análisis, laboratorio, sangre, orina, primer trimestre, segundo trimestre, tercer trimestre, glucosa, biometría hemática, grupo sanguíneo, urocultivo, VDRL, VIH, perfil prenatal, tamizaje, curva de tolerancia, control, seguimiento'),

('EMBARAZO', '¿Cuáles son los síntomas de diabetes gestacional o preeclampsia? ¿Cómo puedo identificarlos?',
 'Es una excelente pregunta. La diabetes gestacional y la preeclampsia son dos complicaciones muy frecuentes del embarazo y estar atenta a los síntomas o cambios que puedes sentir para detectarlos a tiempo, puede ser muy útil para evitar complicaciones mayores. A continuación te detallo lo correspondiente a cada una:

Diabetes gestacional: Este es un problema del metabolismo de los carbohidratos (azúcares), que no siempre presenta síntomas evidentes, pero si detectas alguno de los cambios sutiles que enlisto a continuación, puedes consultarlo con tu médico para hacer los cambios necesarios en tu alimentación y recibir el tratamiento más adecuado para tu caso:
- Mucha sed. En ocasiones la diabetes, entre ella la diabetes gestacional, puede generar que las personas sientan más sed de lo habitual.
- Orinar mucho. Algo característico de la diabetes y la diabetes gestacional es que puede filtrarse más orina por el riñón, para intentar eliminar el exceso de azúcar de la sangre. Esto hace que aumente el número de veces que vamos a orinar y la cantidad de la orina. Pero cuidado, una infección de vías urinarias también podría aumentar el número de veces que sentimos la necesidad de orinar, o incluso al final del embarazo, podríamos sentir mayor urgencia, por la presión del útero sobre nuestra vejiga. Por eso si tienes este síntoma, consúltalo con tu médico, quien te revisará y hará los estudios pertinentes para aclarar lo qué está provocando este síntoma.
- Fatiga extrema. Lo que sucede en todos los tipos de diabetes es que la glucosa no puede ingresar a nuestras células para darles energía y se queda en la sangre. Esto hace que la persona con diabetes sienta un cansancio intenso, principalmente después de comer, cuando debería de tener más energía. Si bien el embarazo, por las hormonas que produce puede hacer que te sientas cansada, éste aparece generalmente por las tardes y de manera habitual deberías reponerte después de una pequeña siesta. Si esto no sucede, deberías platicarlo con tu médico.
- Infecciones frecuentes (en especial de vías urinarias y vaginales). El embarazo hace que el sistema inmunológico de la mujer baje, lo que la hace más propensa a infecciones, pero si a esto le sumamos el factor de tener el azúcar elevado en sangre, puede ocasionar que las infecciones sean más frecuentes. Cuando tengas infecciones de vías urinarias o vaginales muy seguidas en tu embarazo, háblalo con tu médico para que te pueda valorar y en su caso descartar un problema de diabetes gestacional.

Preeclampsia. Este es un problema que es frecuente y puede llegar a ser muy grave, al grado de ser una de las principales causas de muerte de las mujeres embarazadas o en puerperio en México. Por eso, es importante que sepas los signos o síntomas que pueden estar asociados a este problema:
- Dolor de cabeza severo. Si bien en ocasiones podemos tener dolor de cabeza, durante el embarazo estos dolores pueden ser una señal de alarma que indica que tu presión arterial puede estar subiendo y recuerda que la presión alta en el embarazo puede ser en extremo peligrosa para ti y tu bebé. Por eso es importante que si tienes dolor de cabeza intenso, acudas inmediatamente al servicio de urgencias obstetricia que esté más cercano a tu ubicación, no importa si tienes o no seguridad social, en cualquier hospital público te podrán revisar.
- Visión borrosa, ver lucecitas o zumbido de oídos. No es normal que te zumben los oídos o que de pronto veas lucecitas. Estos dos síntomas pueden estar indicando que tu presión está subiendo súbitamente a niveles peligrosos. Lo mejor que puedes hacer, si tienes alguno de estos síntomas es acudir inmediatamente al servicio de urgencias que esté más cerca de ti para que revisen tu presión y vean si requieres un manejo inmediato.
- Hinchazón importante. En la preeclampsia aumenta la presión arterial y esto hace que empecemos a acumular líquido en nuestro cuerpo, lo que genera aumento de volumen en nuestras piernas, brazos o incluso en todo el cuerpo en casos muy severos. Si tú notas que se te hinchan mucho las piernas o alguna parte de tu cuerpo, consulta a tu médico.
- Dolor intenso en la boca del estómago o debajo de las costillas. Es conocido como epigastralgia, es un dolor agudo en la parte alta del estómago, justo por debajo del esternón en la parte media del cuerpo (comúnmente conocido como boca del estómago). Esta zona sensible puede indicarnos diferentes problemas, desde una gastritis, hasta un incremento súbito de la presión arterial con inflamación de las vísceras que requiere atención inmediata. Por eso, si tienes dolor en la boca del estómago, no lo dudes y acude a tu médico inmediatamente, en particular si ese dolor está acompañado de dolor de cabeza, zumbido de oídos, ver lucecitas o presión alta.',
 'diabetes gestacional, preeclampsia, síntomas, presión alta, azúcar, edema, dolor estómago, sed, orinar mucho, fatiga, infecciones frecuentes, visión borrosa, lucecitas, zumbido oídos, hinchazón, epigastralgia, complicaciones, identificar');

select * from chatbot_preguntas;

CREATE TABLE cuestionario_salud (
    id_cuestionario INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_user INT UNSIGNED NOT NULL,
    
    -- Informació1n básica
    primer_embarazo BOOLEAN,
    
    -- Condiciones generales primer embarazo
    obesidad BOOLEAN,
    sobrepeso BOOLEAN,
    presion_arterial_alta BOOLEAN,
    diabetes_mellitus BOOLEAN,
    epilepsia BOOLEAN,
    nefropatia BOOLEAN,
    lupus BOOLEAN,
    desnutricion BOOLEAN,
    enfermedad_tiroidea BOOLEAN,
    otras_enfermedades BOOLEAN,
    descripcion_otras TEXT,
    
    -- Enfermedades del pulmón (desplegable)
    enfermedades_pulmon BOOLEAN,
    asma BOOLEAN,
    tuberculosis BOOLEAN,
    epoc BOOLEAN,
    fibrosis_quistica BOOLEAN,
    neumonia_recurrente BOOLEAN,
    otras_pulmonares BOOLEAN,
    descripcion_otras_pulmonares TEXT,
    
    -- Enfermedades del corazón (desplegable)
    enfermedades_corazon BOOLEAN,
    cardiopatia_congenita BOOLEAN,
    valvulopatia BOOLEAN,
    arritmias BOOLEAN,
    miocardiopatia BOOLEAN,
    hipertension_pulmonar BOOLEAN,
    otras_cardiaca BOOLEAN,
    descripcion_otras_cardiaca TEXT,
    
    -- Enfermedades hematológicas (desplegable)
    enfermedades_hematologicas BOOLEAN,
    anemia BOOLEAN,
    trombocitopenia BOOLEAN,
    hemofilia BOOLEAN,
    leucemia BOOLEAN,
    anemia_falciforme BOOLEAN,
    otras_hematologicas BOOLEAN,
    descripcion_otras_hematologicas TEXT,
    
    -- Enfermedades de Transmisión Sexual (desplegable)
    enfermedades_transmision_sexual BOOLEAN,
    vih BOOLEAN,
    sifilis BOOLEAN,
    gonorrea BOOLEAN,
    clamidia BOOLEAN,
    herpes_genital BOOLEAN,
    vph BOOLEAN,
    otras_ets BOOLEAN,
    descripcion_otras_ets TEXT,
    
    -- Para embarazos anteriores
    numero_embarazos INT,
    numero_partos INT,
    numero_cesareas INT,
    numero_abortos INT,
    numero_ectopicos INT,
    
    -- Complicaciones en embarazos anteriores
    trastornos_hipertensivos BOOLEAN,
    desprendimiento_placenta BOOLEAN,
    parto_pretermino BOOLEAN,
    diabetes_gestacional BOOLEAN,
    restriccion_crecimiento BOOLEAN,
    muerte_fetal BOOLEAN,
    
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES usuaria(id_User)
);

select * from cuestionario_salud;

create table conocimiento_general(
	id_Conocimiento INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_User INT UNSIGNED NOT NULL,
    grupo_sanguineo enum('O', 'A', 'B', 'AB', 'No_se'),
    rh enum('Positivo', 'Negativo'),
    seguro_social enum('Ninguna', 'IMSS', 'ISSSTE', 'IMSS_Bienestar', 'Otra'),
    otro_seguro varchar(100),
    num_afiliacion varchar (11),
    unidad_medica_n1 enum('si', 'no'),
    n1_lugar text,
    
    foreign key (id_User) references usuaria(id_User)
);

CREATE TABLE datos_consultaprenatal (
    id_DC INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_last INT UNSIGNED NOT NULL,
    id_Prenatal INT UNSIGNED,  
    Peso DECIMAL(5,2) NOT NULL,
    IMC DECIMAL(5,2) NOT NULL,
    Presion_Arterial VARCHAR(20),
    fondo_uterino VARCHAR(20),
    Frecuencia_fetal VARCHAR(3),
    medicamentos TEXT,
    datos_generales TEXT,
    fecha_registro DATE DEFAULT (CURRENT_DATE),  
    FOREIGN KEY (id_last) REFERENCES last_mestruacion(id_last),
    FOREIGN KEY (id_Prenatal) REFERENCES ConsultaPrenatales(id_Prenatal) 
);

create table examen_glucosa(
	id_Glucosa INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_glucosa date,
    resultado_glucosa text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);

create table examen_biometrica(
	id_Biometrica INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_biometrica date,
    resultado_biometrica text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);

create table examen_ego(
	id_Ego INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_ego date,
    resultado_ego text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
create table examen_urocultivo(
	id_Urocultivo INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_urocultivo date,
    resultado_urocultivo text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
create table examen_vdrl(	
	id_Vdrl INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_vdrl date,
    resultado_vdrl text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
create table examen_vih(
	id_Vih INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_vih date,
    resultado_vih text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
create table examen_creatinina(
	id_Creatinina INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_creatinina date,
    resultado_creatinina text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)

);
create table examen_acido(
	id_Acido INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_acido date,
    resultado_acido text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
create table examen_citologia(
	id_Citologia INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    fecha_citologia date,
    resultado_citologia text,
	id_User INT UNSIGNED NOT NULL,
	foreign key (id_User) references usuaria(id_User)
);
select * from examen_citologia;

create table otros_examenes(
	id_Otros int unsigned primary key auto_increment,
    tipo_examen text,
    fecha_examen date,
    resultado_examen text,
    id_User int unsigned not null,
    foreign key (id_User) references usuaria(id_User)
);

DELIMITER $$$
CREATE PROCEDURE `CalcularConsultasPrenatales`(
    IN p_id_user INT UNSIGNED,
    IN p_fecha_inicio_embarazo DATE,
    IN p_fecha_registro DATE
)
BEGIN
    DECLARE semanas_transcurridas INT;
    DECLARE v_id_last INT UNSIGNED;
    DECLARE fecha_actual DATE;
    DECLARE contador DATE;
    DECLARE user_exists INT DEFAULT 0;
    
    -- Verificar si el usuario existe
    SELECT COUNT(*) INTO user_exists FROM usuaria WHERE id_User = p_id_user;
    IF user_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El usuario especificado no existe';
    END IF;
    
    -- Usar la fecha actual si no se proporciona fecha de registro
    IF p_fecha_registro IS NULL THEN
        SET fecha_actual = CURDATE();
    ELSE
        SET fecha_actual = p_fecha_registro;
    END IF;
    
    -- Calcular semanas transcurridas desde el inicio del embarazo
    SET semanas_transcurridas = FLOOR(DATEDIFF(fecha_actual, p_fecha_inicio_embarazo) / 7);
    
    -- Insertar o actualizar última menstruación
    IF NOT EXISTS (SELECT 1 FROM last_mestruacion WHERE id_User = p_id_user) THEN
        INSERT INTO last_mestruacion (id_User, ult_mest) VALUES (p_id_user, p_fecha_inicio_embarazo);
        SET v_id_last = LAST_INSERT_ID();
    ELSE
        SELECT id_last INTO v_id_last FROM last_mestruacion WHERE id_User = p_id_user;
        UPDATE last_mestruacion SET ult_mest = p_fecha_inicio_embarazo 
        WHERE id_User = p_id_user;
        
        -- Eliminar consultas existentes para este usuario
        DELETE FROM ConsultaPrenatales WHERE id_last = v_id_last;
    END IF;
    
    -- 1ª consulta: entre 6 - 8 semanas 
    IF semanas_transcurridas < 8 THEN
		-- SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 6 WEEK);
        SET contador = p_fecha_inicio_embarazo;
        WHILE contador <= DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 8 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 2ª consulta: entre 10 - 13.6 semanas 
    IF semanas_transcurridas < 14 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 10 WEEK);
        WHILE contador <= DATE_ADD(DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 13 WEEK), INTERVAL 6 DAY) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 3ª consulta: entre 16 - 18 semanas
    IF semanas_transcurridas < 18 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 16 WEEK);
        WHILE contador <= DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 18 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 4ª consulta: SEMANA 22
    IF semanas_transcurridas < 22 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 22 WEEK);
        WHILE contador < DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 23 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 5ª consulta: SEMANA 28 
    IF semanas_transcurridas < 28 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 28 WEEK);
        WHILE contador < DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 29 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 6ª consulta: SEMANA 32 
    IF semanas_transcurridas < 32 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 32 WEEK);
        WHILE contador < DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 33 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 7ª consulta: SEMANA 36 
    IF semanas_transcurridas < 36 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 36 WEEK);
        WHILE contador < DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 37 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- 8ª consulta: entre 38 - 41 semanas 
    IF semanas_transcurridas < 41 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 38 WEEK);
        WHILE contador <= DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 41 WEEK) DO
            INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
            VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- Retornar información
    SELECT 
        v_id_last AS id_ultima_menstruacion,
        p_fecha_inicio_embarazo AS fecha_inicio_embarazo,
        fecha_actual AS fecha_registro,
        semanas_transcurridas AS semanas_al_registro,
        (SELECT COUNT(*) FROM ConsultaPrenatales WHERE id_last = v_id_last) AS total_fechas_disponibles,
        (SELECT COUNT(DISTINCT DATE(fecha_consulta)) FROM ConsultaPrenatales WHERE id_last = v_id_last) AS dias_unicos_disponibles;
END $$$

DELIMITER ;

DELIMITER $$$
CREATE PROCEDURE `CalcularConsultasUltrasonido`(
    IN p_id_user INT UNSIGNED,
    IN p_fecha_inicio_embarazo DATE,
    IN p_fecha_registro DATE
)
BEGIN
	DECLARE semanas_transcurridas INT;
    DECLARE v_id_last INT UNSIGNED;
    DECLARE fecha_actual DATE;
    DECLARE contador DATE;
    DECLARE user_exists INT DEFAULT 0;
    
    -- Verificar si el usuario existe
    SELECT COUNT(*) INTO user_exists FROM usuaria WHERE id_User = p_id_user;
    IF user_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El usuario especificado no existe';
    END IF;
    
    -- Usar la fecha actual si no se proporciona fecha de registro
    IF p_fecha_registro IS NULL THEN
        SET fecha_actual = CURDATE();
    ELSE
        SET fecha_actual = p_fecha_registro;
    END IF;
    
    -- Calcular semanas transcurridas desde el inicio del embarazo
    SET semanas_transcurridas = FLOOR(DATEDIFF(fecha_actual, p_fecha_inicio_embarazo) / 7);
    
    -- Insertar o actualizar última menstruación
    IF NOT EXISTS (SELECT 1 FROM last_mestruacion WHERE id_User = p_id_user) THEN
        INSERT INTO last_mestruacion (id_User, ult_mest) VALUES (p_id_user, p_fecha_inicio_embarazo);
        SET v_id_last = LAST_INSERT_ID();
    ELSE
        SELECT id_last INTO v_id_last FROM last_mestruacion WHERE id_User = p_id_user;
        UPDATE last_mestruacion SET ult_mest = p_fecha_inicio_embarazo 
        WHERE id_User = p_id_user;
        
        -- Eliminar consultas existentes para este usuario
        DELETE FROM Ultrasonido WHERE id_last = v_id_last;
    END IF;
    
    -- Primera cita de ultrasonido
    IF semanas_transcurridas < 14 THEN
        SET contador = DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 11 WEEK);
        WHILE contador <= DATE_ADD(DATE_ADD(p_fecha_inicio_embarazo, INTERVAL 13 WEEK), INTERVAL 6 DAY) DO
            INSERT INTO Ultrasonido (cita_ultrasonido, id_last) VALUES (contador, v_id_last);
            SET contador = DATE_ADD(contador, INTERVAL 1 DAY);
        END WHILE;
    END IF;
    
    -- Segunda cita de ultrasonido
    if semanas_transcurridas < 25 THEN
		set contador = date_add(p_fecha_inicio_embarazo, interval 18 week);
        while contador <= DATE_ADD(p_fecha_inicio_embarazo, interval 24 week) DO
			insert into Ultrasonido (cita_ultrasonido, id_last) values (contador, v_id_last);
            set contador = date_add(contador, interval 1 day);
		end while;
    END IF;
    
    -- Tercera cita de ultrasonido
    if semanas_transcurridas < 31 THEN
		set contador = date_add(p_fecha_inicio_embarazo, interval 29 week);
        while contador <= DATE_ADD(p_fecha_inicio_embarazo, interval 30 week) DO
			insert into Ultrasonido (cita_ultrasonido, id_last) values (contador, v_id_last);
            set contador = date_add(contador, interval 1 day);
		end while;
    END IF;
    
    SELECT 
        v_id_last AS id_ultima_menstruacion,
        p_fecha_inicio_embarazo AS fecha_inicio_embarazo,
        fecha_actual AS fecha_registro,
        semanas_transcurridas AS semanas_al_registro,
        (SELECT COUNT(*) FROM Ultrasonido WHERE id_last = v_id_last) AS total_fechas_disponibles,
        (SELECT COUNT(DISTINCT DATE(cita_ultrasonido)) FROM Ultrasonido WHERE id_last = v_id_last) AS dias_unicos_disponibles;
END
$$$
DELIMITER ;

DELIMITER $$$
CREATE PROCEDURE `SeleccionarConsultaPrenatal`(
    IN p_id_user INT UNSIGNED, 
    IN p_rango_semanas VARCHAR(20),
    IN p_fecha_seleccionada DATE
)
BEGIN
    DECLARE v_id_last INT UNSIGNED;
    DECLARE fecha_inicio_embarazo DATE;
    DECLARE semana_inicio DATE;
    DECLARE semana_fin DATE;
    DECLARE mensaje_error VARCHAR(200);
    DECLARE fecha_valida INT DEFAULT 0;
    
    -- Obtener datos del usuario
    SELECT id_last, ult_mest INTO v_id_last, fecha_inicio_embarazo 
    FROM last_mestruacion 
    WHERE id_User = p_id_user;
    
    IF v_id_last IS NULL THEN 
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se encontró registro de embarazo para este usuario';
    END IF;
    
    -- Calcular rangos según el parámetro
    CASE p_rango_semanas
        WHEN '0-8' THEN
            SET semana_inicio = fecha_inicio_embarazo;
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 8 WEEK);
            
        WHEN '10-14' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 10 WEEK);
            SET semana_fin = DATE_ADD(DATE_ADD(fecha_inicio_embarazo, INTERVAL 13 WEEK), INTERVAL 6 DAY);
            
        WHEN '16-18' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 16 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 18 WEEK);
            
        WHEN '22' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 22 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 23 WEEK);
            
        WHEN '28' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 28 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 29 WEEK);
            
        WHEN '32' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 32 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 33 WEEK);
            
        WHEN '36' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 36 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 37 WEEK);
            
        WHEN '38-41' THEN
            SET semana_inicio = DATE_ADD(fecha_inicio_embarazo, INTERVAL 38 WEEK);
            SET semana_fin = DATE_ADD(fecha_inicio_embarazo, INTERVAL 41 WEEK);
            
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rango de semanas no válido';
    END CASE;
    
    -- Validar que la fecha esté en el rango
    IF p_fecha_seleccionada BETWEEN semana_inicio AND semana_fin THEN 
        SET fecha_valida = 1;
    END IF;
    
    IF fecha_valida = 0 THEN 
		SET mensaje_error = CONCAT('La fecha seleccionada no está dentro del rango ', p_rango_semanas);
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = mensaje_error;
    END IF;
    
    -- Eliminar otras fechas del mismo rango
    DELETE FROM ConsultaPrenatales 
    WHERE id_last = v_id_last 
    AND fecha_consulta BETWEEN semana_inicio AND semana_fin 
    AND fecha_consulta != p_fecha_seleccionada;
    
    -- Insertar la fecha seleccionada si no existe
    IF NOT EXISTS (
        SELECT 1 FROM ConsultaPrenatales 
        WHERE id_last = v_id_last 
        AND fecha_consulta = p_fecha_seleccionada
    ) THEN 
        INSERT INTO ConsultaPrenatales (fecha_consulta, id_last) 
        VALUES (p_fecha_seleccionada, v_id_last);
    END IF;
    
    SELECT 
        'Fecha seleccionada exitosamente' AS mensaje,
        p_rango_semanas AS rango_semanas,
        semana_inicio AS rango_inicio,
        semana_fin AS rango_fin,
        p_fecha_seleccionada AS fecha_seleccionada,
        (SELECT COUNT(*) FROM ConsultaPrenatales WHERE id_last = v_id_last) AS total_consultas;
END
$$$
DELIMITER ;

DELIMITER $$$
CREATE PROCEDURE `SeleccionarConsultaUltrasonido`(
	IN p_id_user int unsigned,
    IN p_rango_semanas varchar(20),
    IN p_fecha_seleccionada date
)
BEGIN

	declare v_id_last int unsigned;
    declare fecha_inicio_embarazo date;
    declare semana_inicio date;
    declare semana_fin date;
    declare mensaje_error varchar(200);
    declare fecha_valida int default 0;
    
    select id_last, ult_mest into v_id_last, fecha_inicio_embarazo from last_mestruacion
    where id_User = p_id_user;
    
    if v_id_last is null then signal sqlstate '45000' set message_text = 
		'No se encontro registro de embarazo para este usuario';
    end if;
    
    case p_rango_semanas 
    when '10-14' then 
		set semana_inicio = date_add(fecha_inicio_embarazo, interval 11 week);
        set semana_fin = date_add(date_add(fecha_inicio_embarazo, interval 13 week), interval 6 day);
	when '18-24' then
		set semana_inicio = date_add(fecha_inicio_embarazo, interval 18 week);
        set semana_fin = date_add(fecha_inicio_embarazo, interval 24 week);
        
	when '29-30' then 
        set semana_inicio = date_add(fecha_inicio_embarazo, interval 29 week);
        set semana_fin = date_add(fecha_inicio_embarazo, interval 30 week);
	else 
		signal sqlstate '45000' set message_text = 'Rango de semanas no valido';
	end case;
    
    if p_fecha_seleccionada between semana_inicio and semana_fin then set fecha_valida = 1;
    end if;
    
    if fecha_valida = 0 then set mensaje_error = 
		concat('La fecha seleccionada no esta dentro del rango', p_rango_semanas);
		signal sqlstate '45000' set message_text = mensaje_error;
	end if;
    
    delete from Ultrasonido where id_last = v_id_last
    and cita_ultrasonido between semana_inicio and semana_fin
    and cita_ultrasonido != p_fecha_seleccionada;
    
    if not exists(
		select 1 from Ultrasonido where id_last = v_id_last
        and cita_ultrasonido = p_fecha_seleccionada
    ) then 
		insert into Ultrasonido (cita_ultrasonido, is_last)
        values (p_fecha_seleccionada, v_id_last);
	end if;
    
    select 'Fecha seleccionada exitosamente' as mensaje,
		p_rango_semanas as rango_semanas, 
        semana_inicio as rango_inicio,
        semana_fin as rango_fin,
        p_fecha_seleccionada as fecha_seleccionada,
        (select count(*) from Ultrasonido where id_last = v_id_last)
        as total_consultas;
    
END
$$$
DELIMITER ;
