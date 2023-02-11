{
"index.blocks.read_only_allow_delete": "false",
"index.priority": "1",
"index.query.default_field": [
"*"
],
"index.refresh_interval": "1s",
"index.write.wait_for_active_shards": "1",
"index.routing.allocation.include._tier_preference": "data_content",
"index.analysis.filter.my_pos_f.type": "nori_part_of_speech",
"index.analysis.filter.my_pos_f.stoptags": [
"J",
"VV",
"MAG",
"E"
],
"index.analysis.analyzer.korean.type": "nori",
"index.analysis.analyzer.korean.stopwords": "_korean_",
"index.analysis.analyzer.my_analyzer.filter": [
"my_pos_f"
],
"index.analysis.analyzer.my_analyzer.type": "custom",
"index.analysis.analyzer.my_analyzer.tokenizer": "nori_user_dict",
"index.analysis.tokenizer.nori_user_dict.type": "nori_tokenizer",
"index.analysis.tokenizer.nori_user_dict.user_dictionary": "userdict_ko.txt",
"index.analysis.tokenizer.nori_user_dict.decompound_mode": "none",
"index.number_of_replicas": "1"
}