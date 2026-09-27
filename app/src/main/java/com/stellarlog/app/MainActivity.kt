@file:OptIn(ExperimentalMaterial3Api::class)

package com.stellarlog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.stellarlog.app.data.Observation
import com.stellarlog.app.domain.*
import com.stellarlog.app.ui.*
import com.stellarlog.app.ui.theme.StellarTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
 private val vm by viewModels<StellarViewModel> { val a=application as StellarApplication; StellarViewModelFactory(a.observations,a.preferences) }
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { val prefs by vm.settings.collectAsStateWithLifecycle(); val dark=when(prefs.theme){"dark"->true;"light"->false;else->androidx.compose.foundation.isSystemInDarkTheme()}; StellarTheme(dark){ App(vm,prefs.language) } } }
}

private class Copy(private val lang:String) {
 private val en = listOf("Home","Catalog","Journal","Insights","Settings","Good evening, observer","Your sky, remembered.","Tonight’s shortlist","Explore targets","Recent observations","Start your journal","Log observation","No observations yet","Record your first night and your sky story begins here.","Search targets","All types","Search observations","All","Favorites","Nothing found","Sessions","Unique targets","Average rating","Theme","System","Light","Dark","Language","About Stellar Log","An offline-first observing companion. Your notes stay on this device.","New observation","Object","Location","Equipment","Rating","Notes","Save","Cancel","Choose a target","Constellation","Magnitude","Add to journal","Delete","Target library","Across your sessions","This month","Best constellation","Galaxy","Nebula","Cluster","Star","Planet")
 private val ru = listOf("Главная","Каталог","Дневник","Статистика","Настройки","Добрый вечер, наблюдатель","Сохраняйте свои открытия.","Цели на небе","Открыть каталог","Недавние наблюдения","Начните дневник","Записать наблюдение","Пока нет наблюдений","Запишите первую ночь — и начнётся история ваших открытий.","Поиск объектов","Все типы","Поиск наблюдений","Все","Избранное","Ничего не найдено","Сессий","Объектов","Средняя оценка","Тема","Системная","Светлая","Тёмная","Язык","О приложении","Офлайн-дневник наблюдений. Записи хранятся на устройстве.","Новое наблюдение","Объект","Место","Оборудование","Оценка","Заметки","Сохранить","Отмена","Выберите объект","Созвездие","Зв. величина","В дневник","Удалить","Каталог объектов","По вашим наблюдениям","За месяц","Лучшее созвездие","Галактика","Туманность","Скопление","Звезда","Планета")
 private val kk = listOf("Басты бет","Каталог","Күнделік","Статистика","Баптаулар","Қайырлы кеш, бақылаушы","Аспан естеліктеріңіз.","Бүгінгі нысандар","Каталогты ашу","Соңғы бақылаулар","Күнделікті бастаңыз","Бақылауды жазу","Әзірге бақылау жоқ","Алғашқы түніңізді жазып, аспан тарихын бастаңыз.","Нысандарды іздеу","Барлық түрі","Бақылауларды іздеу","Барлығы","Таңдаулылар","Ештеңе табылмады","Сессиялар","Нысандар","Орташа баға","Тақырып","Жүйелік","Ашық","Қараңғы","Тіл","Stellar Log туралы","Офлайн бақылау күнделігі. Жазбалар осы құрылғыда сақталады.","Жаңа бақылау","Нысан","Орын","Жабдық","Бағалау","Жазбалар","Сақтау","Бас тарту","Нысанды таңдаңыз","Шоқжұлдыз","Жарқырауы","Күнделікке қосу","Жою","Нысандар каталогы","Бақылауларыңыз бойынша","Осы айда","Үздік шоқжұлдыз","Галактика","Тұмандық","Шоғыр","Жұлдыз","Ғаламшар")
 private val a=when(lang){"ru"->ru;"kk"->kk;else->en}
 operator fun get(i:Int)=a[i]
 fun type(t:String)=when(t){"Galaxy"->a[46];"Nebula"->a[47];"Cluster"->a[48];"Star"->a[49];else->a[50]}
}
private data class Tab(val route:String,val label:String,val icon:androidx.compose.ui.graphics.vector.ImageVector)

@Composable private fun App(vm:StellarViewModel,lang:String) {
 val c=Copy(lang); val nav= rememberNavController(); val obs by vm.observations.collectAsStateWithLifecycle(); var add by remember { mutableStateOf(false) }; var initial by remember { mutableStateOf<CelestialTarget?>(null) }
 val tabs=listOf(Tab("home",c[0],Icons.Default.Home),Tab("catalog",c[1],Icons.Default.Explore),Tab("journal",c[2],Icons.Default.MenuBook),Tab("insights",c[3],Icons.Default.Insights),Tab("settings",c[4],Icons.Default.Settings)); val entry by nav.currentBackStackEntryAsState()
 Scaffold(topBar={TopAppBar(title={Text("✦ Stellar Log",fontWeight=FontWeight.Bold)},actions={IconButton(onClick={add=true}){Icon(Icons.Default.Add,c[11])}})},bottomBar={NavigationBar{tabs.forEach{t->NavigationBarItem(entry?.destination?.route==t.route,{nav.navigate(t.route){popUpTo(nav.graph.findStartDestination().id){saveState=true};launchSingleTop=true;restoreState=true}},{Icon(t.icon,t.label)},label={Text(t.label)})}}},floatingActionButton={if(entry?.destination?.route!="settings") FloatingActionButton(onClick={add=true}){Icon(Icons.Default.Add,c[11])}}){pad->
  NavHost(nav,"home",Modifier.padding(pad)) {
   composable("home"){Home(obs,c,{add=true},{nav.navigate("catalog")},{nav.navigate("journal")})}
   composable("catalog"){Catalog(c){initial=it;add=true}}
   composable("journal"){Journal(obs,c,vm::favorite,vm::delete)}
   composable("insights"){Insights(obs,c)}
   composable("settings"){Settings(vm,c)}
  }
 }
 if(add) AddDialog(c,initial,{add=false;initial=null}){vm.save(it);add=false;initial=null}
}

@Composable private fun Home(obs:List<Observation>,c:Copy,onAdd:()->Unit,onExplore:()->Unit,onJournal:()->Unit){
 LazyColumn(contentPadding=PaddingValues(18.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
  item{Text(c[5],style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Text(c[6],color=MaterialTheme.colorScheme.onSurfaceVariant);Card(Modifier.padding(top=10.dp),shape=RoundedCornerShape(26.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.primaryContainer)){Column(Modifier.padding(20.dp)){Text("✦  ${c[7]}",style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold);TargetCatalog.targets.take(3).forEach{Text("${it.icon}  ${it.name}  ·  ${it.constellation}",Modifier.padding(top=9.dp).clickable(onClick=onExplore))};TextButton(onClick=onExplore){Text(c[8])}}}}
  item{Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){Stat("${obs.size}",c[20],Modifier.weight(1f));Stat("${obs.map{it.targetId}.distinct().size}",c[21],Modifier.weight(1f))}}
  item{Header(c[9],c[10],onJournal)}
  if(obs.isEmpty()) item{Card(shape=RoundedCornerShape(24.dp)){Column(Modifier.fillMaxWidth().padding(22.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("🌠",style=MaterialTheme.typography.displayMedium);Text(c[12],style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold);Text(c[13]);Button(onClick=onAdd){Text(c[11])}}}}
  else items(obs.take(4)){ObsCard(it,c,{}, {})}
  item{Spacer(Modifier.height(64.dp))}
 }
}

@Composable private fun Catalog(c:Copy,onLog:(CelestialTarget)->Unit){var q by remember{mutableStateOf("")};var filter by remember{mutableStateOf("")};val list=TargetCatalog.targets.filter{(it.name.contains(q,true)||it.constellation.contains(q,true))&&(filter.isEmpty()||it.type==filter)}
 Column(Modifier.fillMaxSize().padding(horizontal=16.dp)){Text(c[43],style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=10.dp));OutlinedTextField(q,{q=it},Modifier.fillMaxWidth(),placeholder={Text(c[14])},leadingIcon={Icon(Icons.Default.Search,null)},singleLine=true);Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){FilterChip(filter.isEmpty(),{filter=""},label={Text(c[15])});listOf("Galaxy","Nebula","Cluster","Star","Planet").forEach{FilterChip(filter==it,{filter=if(filter==it)"" else it},label={Text(c.type(it))})}}
 LazyColumn(verticalArrangement=Arrangement.spacedBy(9.dp),contentPadding=PaddingValues(bottom=18.dp)){items(list){t->Card(shape=RoundedCornerShape(20.dp)){Column(Modifier.fillMaxWidth().padding(15.dp)){Row(verticalAlignment=Alignment.CenterVertically){Text(t.icon,style=MaterialTheme.typography.headlineMedium);Column(Modifier.weight(1f).padding(start=10.dp)){Text(t.name,style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold);Text(t.detail,color=MaterialTheme.colorScheme.onSurfaceVariant)};AssistChip({},label={Text(c.type(t.type))})};Text(t.description,Modifier.padding(top=8.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){Text("${c[39]}: ${t.constellation} · ${c[40]} ${t.magnitude}",style=MaterialTheme.typography.labelMedium);TextButton(onClick={onLog(t)}){Text(c[41])}}}}};if(list.isEmpty())item{Text(c[19])}}
 }
}

@Composable private fun Journal(obs:List<Observation>,c:Copy,onFav:(Long)->Unit,onDelete:(Observation)->Unit){var q by remember{mutableStateOf("")};var fav by remember{mutableStateOf(false)};val list=obs.filter{(it.targetName.contains(q,true)||it.notes.contains(q,true)||it.location.contains(q,true))&&(!fav||it.isFavorite)}
 Column(Modifier.fillMaxSize().padding(horizontal=16.dp)){Text(c[2],style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=10.dp));OutlinedTextField(q,{q=it},Modifier.fillMaxWidth(),placeholder={Text(c[16])},leadingIcon={Icon(Icons.Default.Search,null)},singleLine=true);Row{FilterChip(!fav,{fav=false},label={Text(c[17])});Spacer(Modifier.width(7.dp));FilterChip(fav,{fav=true},label={Text("♥ ${c[18]}")})};LazyColumn(verticalArrangement=Arrangement.spacedBy(9.dp),contentPadding=PaddingValues(bottom=20.dp)){items(list,key={it.id}){o->ObsCard(o,c,{onFav(o.id)},{onDelete(o)})};if(list.isEmpty())item{Text(if(obs.isEmpty())c[13] else c[19],Modifier.padding(20.dp))}}
 }
}
@Composable private fun ObsCard(o:Observation,c:Copy,favorite:()->Unit,delete:()->Unit){Card(shape=RoundedCornerShape(20.dp)){Column(Modifier.fillMaxWidth().padding(14.dp)){Row(verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(o.targetName,style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold);Text("${SimpleDateFormat("d MMM yyyy",Locale.getDefault()).format(Date(o.observedAt))}${if(o.location.isNotBlank())" · ${o.location}" else ""}",style=MaterialTheme.typography.bodySmall)};IconButton(onClick=favorite){Icon(if(o.isFavorite)Icons.Default.Favorite else Icons.Default.FavoriteBorder,c[18])};IconButton(onClick=delete){Icon(Icons.Default.DeleteOutline,c[42])}};Text("★".repeat(o.rating)+"☆".repeat(5-o.rating),color=MaterialTheme.colorScheme.tertiary);if(o.notes.isNotBlank())Text(o.notes,Modifier.padding(top=5.dp));if(o.equipment.isNotBlank())Text("⌕ ${o.equipment}",style=MaterialTheme.typography.labelMedium)}}}

@Composable private fun Insights(obs:List<Observation>,c:Copy){val avg=if(obs.isEmpty())"—" else String.format(Locale.US,"%.1f",obs.map{it.rating}.average());val best=obs.groupingBy{it.category}.eachCount().maxByOrNull{it.value}?.key?:"—";val month=obs.count{System.currentTimeMillis()-it.observedAt<30L*24*60*60*1000};LazyColumn(contentPadding=PaddingValues(18.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){item{Text(c[3],style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Text(c[44],color=MaterialTheme.colorScheme.onSurfaceVariant)};item{Stat("${obs.size}",c[20],Modifier.fillMaxWidth())};item{Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){Stat("${obs.map{it.targetId}.distinct().size}",c[21],Modifier.weight(1f));Stat(avg,c[22],Modifier.weight(1f))}};item{Stat("$month",c[45],Modifier.fillMaxWidth())};item{Card{Column(Modifier.padding(18.dp)){Text(c[46]);Text(best,style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold)}}}}}

@Composable private fun Settings(vm:StellarViewModel,c:Copy){val prefs by vm.settings.collectAsStateWithLifecycle();LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){item{Text(c[4],style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold)};item{Text(c[23],style=MaterialTheme.typography.titleMedium)};item{Row(horizontalArrangement=Arrangement.spacedBy(7.dp)){listOf("system" to c[24],"light" to c[25],"dark" to c[26]).forEach{(v,l)->FilterChip(prefs.theme==v,{vm.theme(v)},label={Text(l)})}}};item{Text(c[27],style=MaterialTheme.typography.titleMedium)};item{Row(horizontalArrangement=Arrangement.spacedBy(7.dp)){listOf("en" to "English","ru" to "Русский","kk" to "Қазақша").forEach{(v,l)->FilterChip(prefs.language==v,{vm.language(v)},label={Text(l)})}}};item{Card{Column(Modifier.padding(18.dp)){Text(c[28],style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold);Text(c[29],Modifier.padding(top=7.dp));Text("Stellar Log · 1.0.0",Modifier.padding(top=14.dp),style=MaterialTheme.typography.labelMedium)}}}}}

@Composable private fun AddDialog(c:Copy,initial:CelestialTarget?,dismiss:()->Unit,save:(Observation)->Unit){var target by remember{mutableStateOf(initial)};var place by remember{mutableStateOf("")};var equipment by remember{mutableStateOf("")};var notes by remember{mutableStateOf("")};var rating by remember{mutableIntStateOf(3)};var expanded by remember{mutableStateOf(false)}
 AlertDialog(onDismissRequest=dismiss,title={Text(c[30])},text={Column(verticalArrangement=Arrangement.spacedBy(7.dp)){ExposedDropdownMenuBox(expanded,{expanded=it}){OutlinedTextField(target?.name?:c[38],{},Modifier.menuAnchor().fillMaxWidth(),readOnly=true,label={Text(c[31])},trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded)});ExposedDropdownMenu(expanded,{expanded=false}){TargetCatalog.targets.forEach{t->DropdownMenuItem(text={Text("${t.icon} ${t.name}")},onClick={target=t;expanded=false})}}};OutlinedTextField(place,{place=it},label={Text(c[32])},singleLine=true);OutlinedTextField(equipment,{equipment=it},label={Text(c[33])},singleLine=true);Row(verticalAlignment=Alignment.CenterVertically){Text("${c[34]}: ");(1..5).forEach{n->Text(if(n<=rating)"★" else "☆",Modifier.clickable{rating=n}.padding(4.dp),color=MaterialTheme.colorScheme.tertiary)}};OutlinedTextField(notes,{notes=it},label={Text(c[35])},minLines=2)}},confirmButton={TextButton(enabled=target!=null,onClick={target?.let{save(Observation(targetId=it.id,targetName=it.name,category=it.constellation,location=place,equipment=equipment,rating=rating,notes=notes))}}){Text(c[36])}},dismissButton={TextButton(onClick=dismiss){Text(c[37])}})
}
@Composable private fun Stat(value:String,label:String,modifier:Modifier=Modifier){Card(modifier,shape=RoundedCornerShape(21.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.secondaryContainer)){Column(Modifier.padding(17.dp)){Text(value,style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Text(label,style=MaterialTheme.typography.labelLarge)}}}
@Composable private fun Header(title:String,action:String,onClick:()->Unit){Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){Text(title,Modifier.weight(1f),style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold);TextButton(onClick=onClick){Text(action)}}}