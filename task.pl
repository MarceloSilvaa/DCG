verbal_phrase(verb(corre)) --> [corre].
verbal_phrase(verb(correu)) --> [correu].
verbal_phrase(verb(bateu)) --> [bateu].
verbal_phrase(verb(leva)) --> [leva].
verbal_phrase(verb(levou)) --> [levou].
verbal_phrase(verb(bate)) --> [bate].

verbal_phrase_p(verb_p(corriam)) --> [corriam].
verbal_phrase_p(verb_p(bateram)) --> [bateram].
verbal_phrase_p(verb_p(correm)) --> [correm].
verbal_phrase_p(verb_p(batem)) --> [batem].
verbal_phrase_p(verb_p(correram)) --> [correram].

determiner_f(det('A')) --> ['A'].
determiner_f(det('a')) --> [a].
determiner_f(det('pela')) --> [pela].
determiner_f(det('na')) --> [na].
noun_f(noun(menina)) --> [menina].
noun_f(noun(vida)) --> [vida].
noun_f(noun(noticia)) --> [noticia].
noun_f(noun(porta)) --> [porta].
noun_f(noun(floresta)) --> [floresta].
noun_f(noun(mae)) --> [mae].
noun_f(noun(cidade)) --> [cidade].
noun_f(noun(lagrima)) --> [lagrima].

determiner_m(det('O')) --> ['O'].
determiner_m(det(o)) --> [o].
determiner_m(det(pelo)) --> [pelo].
determiner_m(det(no)) --> [no].

noun_m(noun(tempo)) --> [tempo].
noun_m(noun(cacador)) --> [cacador].
noun_m(noun(rio)) --> [rio].
noun_m(noun(vento)) --> [vento].
noun_m(noun(martelo)) --> [martelo].
noun_m(noun(sino)) --> [sino].
noun_m(noun(rosto)) --> [rosto].
noun_m(noun(mar)) --> [mar].
noun_m(noun(cachorro)) --> [cachorro].
noun_m(noun(tambor)) --> [tambor].
noun_m(noun(lobo)) --> [lobo].

determiner_p_f(det('As')) --> ['As'].
determiner_p_f(det('as')) --> [as].
determiner_p_f(det('pelas')) --> [pelas].
determiner_p_f(det('nas')) --> [nas].
noun_p_f(noun_p(meninas)) --> [meninas].
noun_p_f(noun_p(vidas)) --> [vidas].
noun_p_f(noun_p(noticias)) --> [noticias].
noun_p_f(noun_p(portas)) --> [portas].
noun_p_f(noun_p(florestas)) --> [florestas].
noun_p_f(noun_p(maes)) --> [maes].
noun_p_f(noun_p(cidades)) --> [cidades].
noun_p_f(noun_p(lagrimas)) --> [lagrimas].

determiner_p_m(det('Os')) --> ['Os'].
determiner_p_m(det('os')) --> [os].
determiner_p_m(det('pelos')) --> [pelos].
determiner_p_m(det('nos')) --> [nos].
noun_p_m(noun_p(tempos)) --> [tempos].
noun_p_m(noun_p(cacador)) --> [cacador].
noun_p_m(noun_p(rios)) --> [rios].
noun_p_m(noun_p(ventos)) --> [ventos].
noun_p_m(noun_p(martelos)) --> [martelos].
noun_p_m(noun_p(sinos)) --> [sinos].
noun_p_m(noun_p(rostos)) --> [rostos].
noun_p_m(noun_p(mares)) --> [mares].
noun_p_m(noun_p(cachorros)) --> [cachorros].
noun_p_m(noun_p(tambores)) --> [tambores].
noun_p_m(noun_p(lobos)) --> [lobos].

complement(complement(para)) --> [para].
complement(complement(com)) --> [com].

sentence(sent(NP)) --> noun_phrase(NP).
sentence(sent(NP,VP)) --> noun_phrase(NP), verbal_phrase(VP).
sentence(sent(NP,VP,CP,NP2)) --> noun_phrase(NP), verbal_phrase(VP), complement(CP), noun_phrase(NP2).
sentence(sent(NP,VP,CP,NP2)) --> noun_phrase(NP), verbal_phrase(VP), complement(CP), noun_phrase_p(NP2).
sentence(sent(NP,VP, NP2)) --> noun_phrase(NP), verbal_phrase(VP), noun_phrase(NP2).

sentence(sent(NP)) --> noun_phrase_p(NP).
sentence(sent(NP,VP)) --> noun_phrase_p(NP), verbal_phrase_p(VP).
sentence(sent(NP,VP,CP,NP2)) --> noun_phrase_p(NP), verbal_phrase_p(VP), complement(CP), noun_phrase_p(NP2).
sentence(sent(NP,VP,CP,NP2)) --> noun_phrase_p(NP), verbal_phrase_p(VP), complement(CP), noun_phrase(NP2).
sentence(sent(NP,VP,NP2)) --> noun_phrase_p(NP), verbal_phrase_p(VP), noun_phrase(NP2).
sentence(sent(NP,VP)) --> noun_phrase_p(NP), verbal_phrase_p(VP).

noun_phrase(noun_phrase(DP, NP)) --> determiner_f(DP), noun_f(NP).
noun_phrase(noun_phrase(DP, NP)) --> determiner_m(DP), noun_m(NP).

noun_phrase(noun_phrase(NP)) --> noun_f(NP).
noun_phrase(noun_phrase(NP)) --> noun_m(NP).

noun_phrase_p(noun_phrase_p(DP, NP)) --> determiner_p_f(DP), noun_p_f(NP).
noun_phrase_p(noun_phrase_p(DP, NP)) --> determiner_p_m(DP), noun_p_m(NP).

noun_phrase_p(noun_phrase_p(NP)) --> noun_p_f(NP).
noun_phrase_p(noun_phrase_p(NP)) --> noun_p_m(NP).

