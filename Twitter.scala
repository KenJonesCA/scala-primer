import com.twitter.util._

case class User(id: Long, screenName: String)

case class Tweet(id: Long, userId: Long, text: String)

case class Timeline(id: Long, tweets: Seq[Long])

object Users {
  val ids: Seq[Long] = 1L to 5L
  lazy val getAll: Seq[User] =
    ids.zip(Vector("Amy", "Bill", "Cathy", "David", "Eve")).map { case (id, name) => User(id, name)}
  def get(id: Long): User = getAll(id.toInt - 1)
}

object UserRepo {
  def get(id: Long): Option[User] = {
    if (Users.ids.contains(id)) Some(Users.get(id)) else None
  }
}

object UserServer {
  class UnknownUserException(msg: String) extends Exception(msg)
  def get(id: Long): Future[User] = {
    if (Users.ids.contains(id))
      Future.value(Users.get(id))
    else
      Future.exception(new UnknownUserException(s"Invalid user: $id"))
  }
}

object Tweets {
  import scala.util.Random.nextInt
  val ids: Seq[Long] = 1L to 50L
  val allText = Vector(
    "Aenean sed metus dui. Vestibulum.",
    "Praesent quam sapien, fringilla vel ornare nec, elementum ornare ante.",
    "Ut lacinia turpis leo, in interdum enim ullamcorper quis. Vivamus facilisis facilisis faucibus. Pellentesque placerat.",
    "Quisque sed hendrerit nisl, sed accumsan mauris. Suspendisse in efficitur mi, quis faucibus libero. Cras sollicitudin vel justo in volutpat.",
    "Sed eget tempus mauris, at tristique libero. Mauris ut ultricies arcu. Vestibulum sit amet volutpat.",
    "Sed pretium nunc et nisl gravida pretium. Morbi ac venenatis purus. Nulla porta ac lectus et eleifend. Sed orci aliquam.",
    "Phasellus et lorem volutpat, feugiat felis vitae, tincidunt massa.",
    "Phasellus aliquet lacus at lectus vestibulum gravida.",
    "Phasellus pellentesque neque quis nisl posuere, at tristique risus viverra.",
    "Vivamus viverra odio a dignissim congue.",
    "In convallis velit non felis dapibus, eu eleifend magna malesuada.",
    "Curabitur egestas lectus viverra, finibus mi vitae, volutpat arcu.",
    "Sed vitae velit volutpat, ullamcorper lacus ac, suscipit nibh.",
    "Praesent at quam et magna faucibus fringilla.",
    "Donec non ipsum quis diam aliquet hendrerit ut et dui.",
    "Phasellus vel justo nec lectus placerat congue at sit amet tellus.",
    "Nullam pretium nulla ut ipsum pharetra, a hendrerit leo mollis.",
    "Sed suscipit libero a ipsum sodales, a bibendum diam ultricies.",
    "Proin maximus elit nec justo dapibus aliquet.",
    "Nulla faucibus lacus non ex aliquam pretium."
  )
  lazy val getAll: Seq[Tweet] = ids.map { id =>
    Tweet(id, Users.ids(nextInt(Users.ids.length)), allText(nextInt(allText.length)))
  }
  def get(id: Long): Tweet = getAll(id.toInt - 1)
}

object TweetsRepo {
  def get(id: Long): Option[Tweet] = {
    if (Tweets.ids.contains(id)) Some(Tweets.get(id)) else None
  }
}

object Timelines {
  import scala.util.Random.nextInt
  val ids: Seq[Long] = 1L to 10L
  lazy val getAll: Seq[Timeline] = ids.map { id =>
    Timeline(id, Seq.fill(5 + nextInt(20))(Tweets.ids(nextInt(Tweets.ids.length))).toSet.toVector)
  }
  def get(id: Long): Timeline = getAll(id.toInt - 1)
}

object TimelinesRepo {
  def get(id: Long): Option[Timeline] = {
    if (Timelines.ids.contains(id)) Some(Timelines.get(id)) else None
  }
}

/* Extra text string

Vector(
  "Praesent non eros dignissim, gravida tellus porttitor, rhoncus massa.",
  "Quisque varius enim a porttitor aliquet.",
  "Vestibulum dictum lectus in augue mollis, vel pretium mi malesuada.",
  "Proin vestibulum est nec porttitor pretium.",
  "Nam vitae orci vehicula, rhoncus tellus in, molestie odio.",
  "Vivamus id lectus vitae ligula mattis pharetra.",
  "In quis mauris convallis, gravida magna ac, convallis tellus.",
  "Suspendisse at purus eget ligula gravida pulvinar eu eget ligula.",
  "Sed faucibus turpis et mauris rutrum tincidunt.",
  "Vestibulum molestie lacus a enim convallis malesuada.",
  "Nullam lobortis ipsum nec risus venenatis pulvinar.",
  "Proin convallis arcu non diam aliquet mattis.",
  "Fusce aliquam urna volutpat elementum dapibus.",
  "Aenean malesuada mauris venenatis justo consequat, id fermentum purus lobortis.",
  "Nulla laoreet diam a ligula sodales pretium.",
  "Nam quis dolor ut nunc consequat laoreet.",
  "Curabitur in orci imperdiet, eleifend sapien quis, vulputate lorem.",
  "Sed vitae risus finibus, iaculis orci in, euismod erat.",
  "Duis id velit euismod, mattis lacus id, egestas nulla.",
  "Suspendisse eu dui vel sem iaculis semper et id erat.",
  "Fusce non nibh vitae lacus faucibus rhoncus eget ut quam.",
  "Nunc vitae diam ac erat accumsan gravida eu vel nisi.",
  "Sed faucibus ligula nec est vestibulum condimentum.",
  "Quisque posuere turpis eu mauris luctus, eu ullamcorper ligula lacinia.",
  "Nunc tincidunt est at lacus feugiat, a vestibulum nisl convallis.",
  "Nulla id ligula quis quam sollicitudin tempus.",
  "Suspendisse accumsan libero ac orci interdum, in pharetra quam tempor.",
  "Curabitur in velit sit amet nibh rhoncus egestas non ut arcu.",
  "Etiam condimentum tellus a libero ullamcorper imperdiet.",
  "Sed id tellus non sapien vehicula sagittis.",
  "Aenean bibendum magna at libero placerat eleifend.",
  "Quisque et elit quis metus viverra finibus.",
  "Sed ullamcorper erat ac est tincidunt, vitae aliquam odio imperdiet.",
  "In auctor quam at purus fringilla vestibulum.",
  "Integer dictum ligula at magna finibus sollicitudin.",
  "Sed ac nibh nec diam aliquet maximus vitae nec risus.",
  "Mauris consequat leo eu enim dignissim tristique.",
  "Curabitur non mauris in ante fermentum lobortis.",
  "Nam et leo dignissim, viverra justo at, vestibulum mauris.",
  "Sed sed arcu sed odio interdum placerat.",
  "Quisque volutpat elit sit amet luctus ultrices.",
  "Aenean varius velit sed justo convallis posuere.",
  "Curabitur eleifend erat in condimentum dignissim.",
  "Vivamus ut nibh volutpat, condimentum magna id, fermentum elit.",
  "Proin a quam pellentesque, blandit tellus non, hendrerit ante.",
  "Curabitur cursus magna vel sem placerat, vitae lobortis est iaculis.",
  "Quisque malesuada nibh quis nibh auctor, ac imperdiet nibh iaculis.",
  "Aliquam ut sapien efficitur, posuere nulla at, vulputate massa.",
  "Integer pharetra massa eget pharetra luctus.",
  "Sed semper enim at felis imperdiet pellentesque.",
  "Vestibulum eu erat eget diam consectetur rhoncus.",
  "Morbi sed leo et erat pulvinar tincidunt.",
  "Vestibulum egestas erat quis viverra sagittis.",
  "Integer quis leo at metus finibus rutrum.",
  "Etiam eu sapien sed nibh tincidunt elementum.",
  "Sed et ex eget urna consectetur pharetra sit amet tempor dolor.",
  "Mauris facilisis odio quis lectus aliquet tincidunt.",
  "Integer aliquet ipsum eget tincidunt vulputate.",
  "Nam ut turpis rutrum, tempus diam nec, imperdiet nisi.",
  "Nulla nec eros accumsan, accumsan tellus non, sollicitudin sapien.",
  "Quisque varius eros sed tempus faucibus.",
  "Fusce lobortis dui vitae sapien fringilla pulvinar.",
  "Pellentesque eget tellus aliquet, pulvinar lacus rutrum, iaculis tortor.",
  "Integer rhoncus urna sed iaculis aliquam.",
  "Vestibulum sollicitudin ex at magna aliquet, a luctus leo mollis.",
  "Nullam vehicula nunc suscipit nulla tempor accumsan.",
  "Curabitur scelerisque justo sed nisi semper, at pellentesque nibh faucibus.",
  "Nunc at nibh eu augue ullamcorper luctus vitae a velit.",
  "Aliquam vel mauris a arcu vehicula sagittis non eget quam.",
  "Integer tincidunt ligula in efficitur efficitur.",
  "Donec eget tortor nec enim egestas efficitur.",
  "Mauris aliquam ipsum tempus nunc eleifend, in molestie diam fringilla.",
  "Etiam mattis turpis sit amet nibh semper, convallis dictum tellus ultricies.",
  "Cras semper nulla sit amet luctus auctor.",
  "In scelerisque magna feugiat velit consectetur, molestie luctus quam fringilla.",
  "Aliquam et mauris vitae magna aliquet vestibulum in quis ante.",
  "Donec sollicitudin dui quis faucibus lacinia.",
  "Maecenas a purus et dolor ullamcorper rhoncus.",
  "Etiam vestibulum tellus id metus ultricies tempor.",
  "Nam posuere mauris ac dictum interdum.",
  "Donec in ante porttitor, egestas enim et, vulputate arcu.",
  "Sed non ipsum eu orci blandit viverra.",
  "Curabitur elementum lorem vel odio venenatis eleifend.",
  "Nulla at erat ut tortor interdum tincidunt.",
  "Integer molestie purus eu mauris interdum imperdiet.",
  "Donec eleifend sapien eget nunc finibus accumsan.",
  "Aliquam in eros vel mauris facilisis tincidunt.",
  "Integer efficitur nibh quis sagittis faucibus.",
  "Curabitur tempus orci et ipsum rutrum, et viverra enim dignissim.",
  "Donec in nunc lobortis, ornare orci a, consectetur purus.",
  "Cras ultricies dolor id mattis efficitur.",
  "Cras semper leo ut erat gravida, sed tincidunt neque tempor.",
  "Aenean sit amet orci varius, posuere enim sit amet, sollicitudin arcu.",
  "Suspendisse interdum nibh sit amet enim pulvinar, eu viverra massa mollis.",
  "Pellentesque pulvinar orci ac libero dignissim, non auctor mauris elementum.",
  "Donec placerat tellus sit amet dui gravida, eget sagittis lacus interdum.",
  "Nunc scelerisque magna sit amet ante rutrum, rutrum congue nulla aliquam.",
  "Maecenas scelerisque est quis massa rhoncus sagittis.",
  "Nam pulvinar tortor ut dolor sagittis, sit amet cursus erat congue.",
  "Cras ut tellus vel enim ultricies blandit ut et turpis.",
  "Etiam pulvinar velit at ante tincidunt imperdiet.",
  "Vestibulum fringilla odio non purus mattis semper.",
  "Aenean hendrerit nibh vitae accumsan interdum.",
  "Donec facilisis nisi et turpis lacinia, id ultricies mauris posuere.",
  "Nunc ultricies dui vitae leo commodo, nec vehicula odio euismod.",
  "Pellentesque et est dapibus, iaculis arcu at, tincidunt leo.",
  "Nullam non ipsum et sapien iaculis consectetur et a massa.",
  "Aenean tempor mi vitae feugiat vehicula.",
  "Vivamus facilisis libero sit amet lorem molestie elementum.",
  "Donec ultricies tellus vitae vehicula placerat.",
  "Donec condimentum magna sit amet sollicitudin maximus.",
  "Donec hendrerit lectus eu viverra ornare.",
  "Aenean porttitor lectus id dignissim suscipit.",
  "Duis vitae nisi vitae elit tristique aliquam.",
  "Sed dictum mi a venenatis porttitor.",
  "Aliquam vitae leo nec purus imperdiet semper.",
  "Morbi iaculis metus ut sapien mattis aliquet.",
  "Curabitur nec lorem facilisis, dictum massa ut, consequat lectus.",
  "Suspendisse nec purus eleifend, porttitor justo ac, interdum tortor.",
  "Aliquam vel velit imperdiet, placerat nisi vel, pellentesque est.",
  "Praesent et urna quis nisl aliquam euismod eu vitae mauris.",
  "Etiam venenatis magna malesuada dui dictum, in rhoncus massa fermentum.",
  "Aenean iaculis sem at metus tristique dignissim id sed leo.",
  "Curabitur posuere justo nec diam convallis, fringilla malesuada ipsum aliquam.",
  "Ut auctor erat et neque tempor posuere.",
  "Morbi ut massa non arcu ultricies dictum eget sed nibh.",
  "Pellentesque eget leo vel nibh cursus ultrices vitae vitae massa.",
  "In sit amet sapien blandit, sollicitudin mauris ut, fringilla eros.",
  "Mauris et magna nec diam gravida posuere sed ut massa.",
  "Donec vitae libero ultricies, sollicitudin nisi non, lobortis sem.",
  "Sed hendrerit dui quis commodo rhoncus.",
  "Cras semper velit et augue posuere vehicula.",
  "In at erat nec erat pretium tincidunt.",
  "Donec in sem vitae nisl gravida dapibus.",
  "Morbi volutpat tortor vel egestas faucibus.",
  "Etiam dignissim arcu auctor orci molestie tincidunt.",
  "Proin ut dolor rutrum, molestie ex in, aliquet tortor.",
  "Sed a libero nec diam tincidunt ornare.",
  "Suspendisse quis augue et turpis luctus lacinia.",
  "Phasellus in metus nec quam fermentum gravida.",
  "Duis suscipit elit a mi pellentesque egestas.",
  "Integer finibus mauris sit amet neque mattis, at maximus nunc lobortis.",
  "Pellentesque eget sapien vestibulum ipsum condimentum tristique id ac lorem.",
  "Maecenas eget sem ac purus porta lacinia vitae non nibh.",
  "In efficitur dui ac fermentum fermentum."
)
*/
