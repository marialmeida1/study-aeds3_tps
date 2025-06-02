package tp03.src.storage.structures.ListaInvertida;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListaInvertidaUtils {

  // lista de stop words em português
  private static String[] stopWords = {
      "a", "o", "que", "e", "do", "da", "em", "um", "para", "é",
      "com", "não", "uma", "os", "no", "se", "na", "por", "mais", "as",
      "dos", "como", "mas", "foi", "ao", "ele", "das", "tem", "à", "seu",
      "sua", "ou", "ser", "quando", "muito", "há", "nos", "já", "está", "eu",
      "também", "só", "pelo", "pela", "até", "isso", "ela", "entre", "era", "depois",
      "sem", "mesmo", "aos", "ter", "seus", "quem", "nas", "me", "esse", "eles",
      "estão", "você", "tinha", "foram", "essa", "num", "nem", "suas", "meu", "às",
      "minha", "têm", "numa", "pelos", "elas", "havia", "seja", "qual", "será", "nós",
      "tenho", "lhe", "deles", "essas", "esses", "pelas", "este", "fosse", "dele", "tu",
      "te", "vocês", "vos", "lhes", "meus", "minhas", "teu", "tua", "teus", "tuas",
      "nosso", "nossa", "nossos", "nossas", "dela", "delas", "esta", "estes", "estas",
      "aquele", "aquela", "aqueles", "aquelas", "isto", "aquilo", "estou", "está",
      "estamos", "estão", "estive", "esteve", "estivemos", "estiveram", "estava",
      "estávamos", "estavam", "estivera", "estivéramos", "esteja", "estejamos",
      "estejam", "estivesse", "estivéssemos", "estivessem", "estiver", "estivermos",
      "estiverem", "hei", "há", "havemos", "hão", "houve", "houvemos", "houveram",
      "houvera", "houvéramos", "haja", "hajamos", "hajam", "houvesse", "houvéssemos",
      "houvessem", "houver", "houvermos", "houverem", "houverei", "houverá",
      "houveremos", "houverão", "houveria", "houveríamos", "houveriam", "sou",
      "somos", "são", "era", "éramos", "eram", "fui", "foi", "fomos", "foram",
      "fora", "fôramos", "seja", "sejamos", "sejam", "fosse", "fôssemos", "fossem",
      "for", "formos", "forem", "serei", "será", "seremos", "serão", "seria",
      "seríamos", "seriam", "tenho", "tem", "temos", "tém", "tinha", "tínhamos",
      "tinham", "tive", "teve", "tivemos", "tiveram", "tivera", "tivéramos",
      "tenha", "tenhamos", "tenham", "tivesse", "tivéssemos", "tivessem",
      "tiver", "tivermos", "tiverem", "terei", "terá", "teremos", "terão",
      "teria", "teríamos", "teriam"
  };

  // Converte o termo pra caixa baixa e remove acentos
  private static String normalize(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }

  // Remove stop words de um array de termos e retorna um novo array sem as stop
  // words
  public String[] extractTerms(String texto) {
    texto = texto.toLowerCase();
    String[] termosSeparados = texto.split(" ");
    ArrayList<String> result = new ArrayList<>();
    for (String termo : termosSeparados) {
      boolean isStopWord = false;
      for (String stopWord : stopWords) {
        if (termo.equals(stopWord)) {
          isStopWord = true;
          break;
        }
      }
      if (!isStopWord && !termo.isEmpty()) {
        result.add(termo);
      }
    }
    return result.toArray(new String[0]);
  }

  private float calcFrequencia(String texto, String termo) {
    // Normalize the input text and term
    texto = texto.toLowerCase();
    termo = termo.toLowerCase();

    // Split the text into terms
    String[] termos = texto.split("\\s+");

    // Count occurrences of the term
    int count = 0;
    for (String t : termos) {
        if (t.equals(termo)) {
            count++;
        }
    }

    // Calculate term frequency (tf)
    return (float) count / termos.length;
}
  
private float calcularIDF(ListaInvertida lista, String termo) throws Exception {
  int totalDocumentos = lista.numeroEntidades();
  ElementoLista[] elementos = lista.read(termo);
  int documentosComTermo = (elementos != null) ? elementos.length : 0;

  if (documentosComTermo == 0) {
      return 0;
  }

  return (float) Math.log((double) totalDocumentos / documentosComTermo);
}
  // na função principal (de ordenar os resultados), normalizar os termos depois da chamada de extractTerms
  // além disso, terá de ser implementada a função para calcular a frequência
}