package getta.gettaroo.commands;

public class LockSlotCommand {
/*
    public static List<LockSlotList.LockSlot> slotsList = new ArrayList<>();

    public static File file = new File(fi.dy.masa.malilib.util.FileUtils.getConfigDirectory(), "LockSlots.json");

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(ClientCommandManager.literal("lock")
                .then(ClientCommandManager.literal("add")
                        .executes(context -> addSlot(Gettaroo.mc.player.inventory.selectedSlot)))
                .then(ClientCommandManager.literal("remove")
                        .executes(context -> removeLock(Gettaroo.mc.player.inventory.selectedSlot)))
                .then(ClientCommandManager.literal("list")
                        .executes(context -> showList())));
    }

    public static int addSlot(int slot){

        if(!Gettaroo.mc.player.inventory.getMainHandStack().isEmpty()) {

            LockSlotList.LockSlot lockSlot = new LockSlotList.LockSlot(Registry.ITEM.getId(Gettaroo.mc.player.inventory.getMainHandStack().getItem()).toString(), slot);
            updateList();

            if(readJsonLock() != null) {

                LockSlotList list1 = readJsonLock();

                for (LockSlotList.LockSlot lockSlot1 : list1.getList()) {

                    switch(lockSlot1.compareTo(lockSlot)){
                        case 0:
                            Gettaroo.mc.inGameHud.getChatHud().addMessage(Text.of("§4This slot is alredy locked"));
                            return 0;

                        case 1:
                            slotsList.remove(new LockSlotList.LockSlot(lockSlot1.getItem(), lockSlot1.getSlot()));

                    }
                }
            }

            slotsList.add(lockSlot);
            LockSlotList list2 = new LockSlotList(slotsList);

            FileUtils.loadToJsonObject(file, list2);

        }else{
            Gettaroo.mc.inGameHud.getChatHud().addMessage(TextUtils.HAND_IS_EMPTY);
        }
        return 1;
    }

    public static int removeLock(int slot){
        updateList();
        slotsList.remove(new LockSlotList.LockSlot(Registry.ITEM.getId(Gettaroo.mc.player.inventory.getMainHandStack().getItem()).toString(), slot));
        LockSlotList list2 = new LockSlotList(slotsList);
        FileUtils.loadToJsonObject(file, list2);
        return 1;
    }

    public static int showList(){
        if(!readJsonLock().getList().isEmpty()) {
            Gettaroo.mc.inGameHud.getChatHud().addMessage(Text.of("§8Current locked slots : "));
            for(LockSlotList.LockSlot item : readJsonLock().getList()){
                Gettaroo.mc.inGameHud.getChatHud().addMessage(Text.of(item.toString()));
            }
        }else{
            Gettaroo.mc.inGameHud.getChatHud().addMessage(Text.of("§4You dont have any slot locked"));
        }
        return 1;
    }

    public static LockSlotList readJsonLock(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            LockSlotList item = gson.fromJson(sb.toString(), LockSlotList.class);

            return item;
        }catch (IOException e){Gettaroo.mc.inGameHud.getChatHud().addMessage(TextUtils.FILE_NOT_FOUND);}
        return null;
    }

    public static void updateList(){
        if(readJsonLock() != null || !readJsonLock().getList().isEmpty()){
            slotsList = readJsonLock().getList();
        }
    }

 */
}
