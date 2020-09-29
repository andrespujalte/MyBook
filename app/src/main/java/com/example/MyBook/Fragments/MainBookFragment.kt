package com.example.MyBook.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MyBook.Adaptadores.BooksListAdapter
import com.example.MyBook.Clases.Book
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainBookFragment : Fragment() {
    lateinit var v : View
    lateinit var rv_books : RecyclerView
    lateinit var books  : MutableList<Book>
    lateinit var book: Book
    lateinit var fbt_main_book_new : FloatingActionButton
    private var db : appDatabase? = null
    private var bookDao : bookDao? = null

    private lateinit var linearLayoutManager: LinearLayoutManager


    private lateinit var bookListAdapter: BooksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main_book , container, false)
        fbt_main_book_new = v.findViewById(R.id.fbt_main_book_new)
        setHasOptionsMenu(true)
        rv_books = v.findViewById(R.id.rv_books)
        books = ArrayList<Book>()
        db = appDatabase.getAppDataBase(v.context)
        bookDao = db?.bookDao()
        if(bookDao?.loadAllBooks()?.isNullOrEmpty()!!)
            onFirstLoginCreateBooksItems()
        books = bookDao!!.loadAllBooks() as MutableList<Book>
        return v
    }


    override fun onStart() {
        super.onStart()
        rv_books.setHasFixedSize(true)
        linearLayoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_books.layoutManager = linearLayoutManager
        bookListAdapter = BooksListAdapter(books){position -> onItemClick(position)}
        rv_books.adapter = bookListAdapter
        fbt_main_book_new.setOnClickListener{
            val actionMainBookFragmentToMainBookNewFragment = MainBookFragmentDirections.actionMainBookFragmentToMainBookNewFragment()
            (v.findNavController().navigate(actionMainBookFragmentToMainBookNewFragment))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_main_book,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = when(item.itemId) {
            R.id.action_All -> {
                books = bookDao?.loadAllBooks()  as MutableList<Book>
                Snackbar.make(v, "Ver todo", Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_literatura_adolecente -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Literatura_Adolencete))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Literatura_Adolencete), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_Terror -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Novelas_Terror))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Novelas_Terror), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_niños -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Literatura_Niños))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Literatura_Niños), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_Universitarios -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Universitarios))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Universitarios), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_divulgacion -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Divulgacion_Cientifica))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Divulgacion_Cientifica), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_ensayos -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Ensayos))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Ensayos), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_policiales -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Novelas_Policiales))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Novelas_Policiales), Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_romanticas -> {
                books = bookDao?.loadBookbyCategory(getString(R.string.Categoria_Novelas_Romanticas))  as MutableList<Book>
                Snackbar.make(v, getString(R.string.Categoria_Novelas_Romanticas), Snackbar.LENGTH_SHORT).show()
            }

            else -> ""
        }
        bookListAdapter = BooksListAdapter(books){position -> onItemClick(position)}
        rv_books.adapter = bookListAdapter
        return super.onOptionsItemSelected(item)
    }


    fun onItemClick(position : Int){
        val actionMainBookFragmentToMainBookDetailContainer = MainBookFragmentDirections.actionMainBookFragmentToMainBookDetailContainer(books[position])
        (v.findNavController().navigate(actionMainBookFragmentToMainBookDetailContainer))
    }
    fun onFirstLoginCreateBooksItems(){
        book = Book(1,"La Sangre Manda","King Stephen"," PLAZA & JANES EDITORES","9789506445386",getString(R.string.Categoria_Novelas_Terror),"https://contentv2.tap-commerce.com/cover/large/9789506445386_1.jpg?id_com=1113","Cuatro novelas cortas de Stephen King sobre las fuerzas ocultas que nos acechan. En esta colección única nos ofrece un impactante noir paranormal, protagonizado por la carismática Holly Gibney, y tres relatos más que ponen de manifiesto el incomparable talento, la imaginación sin par y la diversidad de registros de este legendario narrador.")
        bookDao?.insertBook(book)
        book = Book(2,"1. Harry Potter Y La Piedra Filosofal","Rowling J. K.","SALAMANDRA","9789878000107",getString(R.string.Categoria_Literatura_Adolencete),"https://contentv2.tap-commerce.com/cover/large/9789878000107_1.jpg?id_com=1113","Harry Potter se ha quedado huérfano y vive en casa de sus abominables tíos y del insoportable primo Dudley. Se siente muy triste y solo, hasta que un buen día recibe una carta que cambiará su vida para siempre. En ella le comunican que ha sido aceptado como alumno en el colegio interno Hogwarts de magia y hechicería. A partir de ese momento, la suerte de Harry da un vuelco espectacular. En esa escuela tan especial aprenderá encantamientos, trucos fabulosos y tácticas de defensa contra las malas artes. Se convertirá en el campeón escolar de quidditch, especie de fútbol aéreo que se juega montado sobre escobas, y hará un puñado de buenos amigos... aunque también algunos temibles enemigos. Pero, sobre todo, conocerá los secretos que le permitirán cumplir con su destino. Pues, aunque no lo parezca a primera vista, Harry no es un chico común y corriente. íEs un verdadero mago!")
        bookDao?.insertBook(book)
        book = Book(3,"2. Harry Potter Y La Camara Secreta","Rowling J. K.","SALAMANDRA","9789878000114",getString(R.string.Categoria_Literatura_Adolencete),"https://contentv2.tap-commerce.com/cover/large/9789878000114_1.jpg?id_com=1113","Mientras Harry espera impaciente en casa de sus insoportables tíos el inicio del segundo curso del Colegio Hogwarts de Magia y Hechicería, un elfo aparece en su habitación y le advierte de que una amenaza mortal se cierne sobre la escuela. Harry no se lo piensa dos veces y, acompañado de Ron, se dirige a Hogwarts en un coche volador. Pero, ¿puede un aprendiz de mago defender la escuela de los malvados que pretenden destruirla? Sin saber que alguien ha abierto la Cámara de los Secretos, dejando escapar una serie de monstruos peligrosos, Harry y sus amigos Ron y Hermione tendrán que enfrentarse con arañas gigantes, serpientes encantadas, fantasmas enfurecidos y, sobre todo, con la mismísima reencarnación de su más temible adversario.")
        bookDao?.insertBook(book)
        book = Book(4,"3. Harry Potter Y El Prisionero De Azkaban","Rowling J. K.","SALAMANDRA","9789878000176",getString(R.string.Categoria_Literatura_Adolencete),"https://contentv2.tap-commerce.com/cover/large/9789878000176_1.jpg?id_com=1113","De la prisión de Azkaban se ha escapado un terrible villano, Sirius Black, un asesino en serie que fue cómplice de lord Voldemort y que, dicen los rumores, quiere vengarse de Harry por haber destruido a su maestro. Por si esto fuera poco, entran en acción los dementores, unos seres abominables capaces de robarles la felicidad a los magos y de eliminar todo recuerdo hermoso de aquellos que se atreven a acercárseles. El desafío es enorme, pero Harry, Ron y Hermione son capaces de enfrentarse a todo esto y mucho más.")
        bookDao?.insertBook(book)
        book = Book(5,"4. Harry Potter Y El Caliz De Fuego","Rowling J. K.","SALAMANDRA","9788498389951",getString(R.string.Categoria_Literatura_Adolencete),"https://contentv2.tap-commerce.com/cover/large/9788498389951_1.jpg?id_com=1113","Habrá tres pruebas, espaciadas en el curso escolar, que medirán a los campeones en muchos aspectos diferentes: sus habilidades mágicas, su osadía, sus dotes de deducción y, por supuesto, su capacidad para sortear el peligro.» Se va a celebrar en Hogwarts el Torneo de los Tres Magos. Sólo los alumnos mayores de diecisiete años pueden participar en esta comp etición, pero, aun así, Harry sueña con ganarla. En Halloween, cuando el cáliz de fuego elige a los campeones, Harry se lleva una sorpresa al ver que su nombre es uno de los escogidos por el cáliz mágico. Durante el torneo deberá enfrentarse a desafíos mortales, d ragones y magos tenebrosos, pero con la ayuda de Ron y Hermione, sus mejores amigos, íquizá logre salir con vida!")
        bookDao?.insertBook(book)
        book = Book(6,"5. Harry Potter Y La Orden Del Fenix","Rowling J. K.","SALAMANDRA","9789878000145",getString(R.string.Categoria_Literatura_Adolencete),"https://contentv2.tap-commerce.com/cover/large/9789878000145_1.jpg?id_com=1113","Las tediosas vacaciones de verano en casa de sus tíos todavía no han acabado y Harry se encuentra más inquieto que nunca. Apenas ha tenido noticias de Ron y Hermione, y presiente que algo extraño está sucediendo en Hogwarts. En efecto, cuando por fin comienza otro curso en el famoso colegio de magia y hechicería, sus temores se vuelven realidad. El Ministerio de Magia niega que Voldemort haya regresado y ha iniciado una campaña de desprestigio contra Harry y Dumbledore, para lo cual ha asignado a la horrible profesora Dolores Umbridge la tarea de vigilar todos sus movimientos. Así pues, además de sentirse solo e incomprendido, Harry sospecha que Voldemort puede adivinar sus pensamientos, e intuye que el temible mago trata de apoderarse de un objeto secreto que le permitiría recuperar su poder destructivo.")
        bookDao?.insertBook(book)
        book = Book(7,"Cavernas y palacios","Golombek Diego","SIGLO XXI EDITORES","9789876292443",getString(R.string.Categoria_Divulgacion_Cientifica),"https://contentv2.tap-commerce.com/cover/large/9789876291934_1.jpg?id_com=1113","¿Somos una función del cerebro? ¿Deberemos aceptar que nuestra historia, nuestras emociones, nuestra conciencia, nuestros dolores... todo está codificado en la actividad de unos miles de millones de neuronas? Hoy podemos mirar el cerebro en acción desde afuera, y así vislumbrar sus funciones, las zonas que se activan al ver la imagen de la maestra de primer grado, o al repasar la tabla del siete, o al cantar bajo la lluvia. Pero la comprensión de la conciencia, esa capacidad de saber quiénes somos, que tenemos un cuerpo, que nos duele la punta del dedo, es aún esquiva, aunque hay cierto consenso en que el cerebro tiene mucho -si no todo- que ver con esas cosas. En este libro recorreremos las cavernas y los palacios de la conciencia; tanto esas zonas luminosas que podemos comprender como las cuevas oscuras que el cerebro se resiste a mostrarnos (a nosotros mismos y a los investigadores que las exploran con velas en la mano). Luego de un paseo histórico por las ideas sobre el cerebro y la conciencia, entraremos de lleno en el sistema nervioso a través de las puertas de la percepción, que nos permiten saber que hay un mundo ahí afuera (aunque a veces sean de lo más engañosas). Nos sorprenderemos con diversos experimentos y transitaremos por los bordes de la conciencia perdida... en el cerebro. En suma, se trata de un viaje hacia nosotros mismos.")
        bookDao?.insertBook(book)
        book = Book(8,"El Nuevo Cocinero Cientifico","Golombek Diego","SIGLO XXI EDITORES","9789876291934",getString(R.string.Categoria_Divulgacion_Cientifica),"https://contentv2.tap-commerce.com/cover/large/9789876292443_1.jpg?id_com=1113","Todos tenemos un laboratorio en casa, ese lugar donde hacemos gala de nuestra creatividad y donde además nos divertimos como expertos químicos, físicos, biólogos... y cocineros. Es que cocinar no sólo es la mayor de las bellas artes, sino también una ciencia y uno de los juegos más deliciosos y entretenidos del mundo. Este libro, pensado como un menú científico-culinario desde el desayuno hasta el postre, pasa por el filtro de la ciencia los mitos más arraigados de la sabiduría popular culinaria para determinar cuánto tienen de cierto y cuánto de fábula. Y en el camino, da respuesta a preguntas dignas de un Premio Nobel en Gastronomía: ¿Azúcar o edulcorante? ¿Por qué es roja la carne (roja)? ¿Cómo lograr una mayonesa perfecta? Si nada se pega al teflón, ¿cómo se pega el teflón a la sartén? ¿Es verdad que los vinos y los quesos no se llevan tan bien como dicen? Y todo esto sin dejar de lado ni la espinaca de Popeye ni las especias de Colón ni las frutas del Paraíso. En esta nueva edición, los biólogos Diego Golombek y Pablo Schwarzbaum cuentan todo lo que usted siempre quiso saber y nunca se animó a preguntarle a su cocinero científico amigo. Un libro para equivocarse menos, divertirse a lo grande y deleitar a sus invitados.")
        bookDao?.insertBook(book)
        book = Book(9,"Demoliendo Papers","Golombek Diego","SIGLO XXI EDITORES","9789876292276",getString(R.string.Categoria_Divulgacion_Cientifica),"https://contentv2.tap-commerce.com/cover/large/9789876292276_1.jpg?id_com=1113","¿Por qué, después de largos minutos de espera, encendemos un cigarrillo y aparece inevitablemente el colectivo? ¿Qué es mejor a la hora de exterminar cucarachas: el insecticida o la ojota? El divino botón, ¿es divino? ¿Cómo cae una tostada untada con mermelada si se la ata al lomo de un gato? Si los sándwiches triples de miga tienen tres panes, los simples ¿no deberían llamarse dobles? ¿A qué vienen estas preguntas? ¿Qué tienen que ver con la ciencia? Este libro reúne una selección de papers que fueron escritos siguiendo al pie de la letra las reglas de las publicaciones científicas, pero en los que se desarrollan temáticas tan disparatadas como el humor de las bacterias, el hombre de la bolsa o los virus de los zombies. Es que, como afirma Diego Golombek, aprender a hacer ciencia es también aprender a comunicarla. Y para eso están los papers, esos artículos redactados 'en difícil' en los que los científicos explican y ponen a prueba sus investigaciones.Así, Demoliendo papers propone una visión divertida y satírica de la ciencia a través de textos que ejercitan la creatividad y, muchas veces, la risa (científica, claro).Esto nos sirve para mostrar la otra cara del trabajo con ratones, tubos de ensayo y máquinas de avanzada, y también el buen humor de su científico amigo. Porque, después de todo, desarrollar la imaginación es una de las mejores formas de acercarnos a la ciencia.")
        bookDao?.insertBook(book)
        book = Book(10,"Las Neuronas De Dios","Golombek Diego","SIGLO XXI EDITORES","9789876294799",getString(R.string.Categoria_Divulgacion_Cientifica),"https://contentv2.tap-commerce.com/cover/large/9789876294799_1.jpg?id_com=1113","Muchos pensarán que este es un libro más sobre el eterno enfrentamiento entre la ciencia y la religión, entre la fuerza de la razón y la fuerza de la fe. Nada de eso. Diego Golombek propone una mirada mucho más novedosa e interesante. Por primera vez, las ciencias naturales pueden estudiar la religión en lugar de burlarse de ella; por primera vez, la ciencia puede responder una pregunta inquietante: ¿por qué, en pleno siglo XXI, seguimos creyendo en algo o alguien superior, llámese Dios, meditación trascendental, espiritualidad o sentido de la vida? ¿De dónde surge esta necesidad, antigua como nuestra especie, que en algún momento de nuestras vidas nos lleva a preguntarnos por lo que habrá “después” o lo que está “más allá”? ¿Viene “de fábrica” o es un producto de la cultura? En su viaje al corazón de las creencias, Diego Golombek pasa revista a un sinfín de experimentos que muestran cómo actúan las neuronas de monjas rezadoras, budistas meditadores, pentecostales o iluminados con LSD, peyote, ayahuasca y hongos alucinógenos varios. Sin olvidar a las personas que han atravesado experiencias límite, como trances epilépticos o la vivencia de la propia muerte con la misteriosa luz al final del túnel. Los resultados permiten identificar circuitos neuronales que están en la base de visiones y experiencias místicas.")
        bookDao?.insertBook(book)
    }

}