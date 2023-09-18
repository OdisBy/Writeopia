---
sidebar_position: 0
---

# Customize Behaviour

The base class for behaviour controlling in Writeopia is [WriteopiaManager](https://kdocs.writeopia.io/writeopia/io.writeopia.sdk.manager/-writeopia-manager/index.html?query=class%20WriteopiaManager(stepsNormalizer:%20UnitsNormalizationMap%20=%20StepsMapNormalizationBuilder.reduceNormalizations%20{%20%20%20%20%20%20%20%20%20%20%20%20defaultNormalizers()%20%20%20%20%20%20%20%20},%20movementHandler:%20MovementHandler%20=%20MovementHandler(stepsNormalizer),%20contentHandler:%20ContentHandler%20=%20ContentHandler(%20%20%20%20%20%20%20%20stepsNormalizer%20=%20stepsNormalizer%20%20%20%20),%20focusHandler:%20FocusHandler%20=%20FocusHandler(),%20coroutineScope:%20CoroutineScope%20=%20CoroutineScope(%20%20%20%20%20%20%20%20SupervisorJob()%20+%20Dispatchers.Main.immediate%20%20%20%20),%20dispatcher:%20CoroutineDispatcher%20=%20Dispatchers.IO,%20backStackManager:%20BackstackManager%20=%20BackstackManager.create(%20%20%20%20%20%20%20%20contentHandler,%20%20%20%20%20%20%20%20movementHandler%20%20%20%20),%20userId:%20suspend%20()%20-%3E%20String%20=%20{%20%22no_user_id_provided%22%20})%20:%20BackstackHandler,%20BackstackInform) and this is the entry point for all logic customization, with some exceptions. 

In this section you will learn the default behaviour of Writeopia and how to customize it to fit your needs.
