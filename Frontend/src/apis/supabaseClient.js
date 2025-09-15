import { createClient } from "@supabase/supabase-js";

const supabaseUrl = "https://qyfnstcqropueurlgjuh.supabase.co";
const supabaseKey =
  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF5Zm5zdGNxcm9wdWV1cmxnanVoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQyMTU1NzcsImV4cCI6MjA2OTc5MTU3N30.ky9Kps-KCRYX91FjfgxjouYP6us82PY5a4281K_qmhs";
const supabaseClient = createClient(supabaseUrl, supabaseKey);

export default supabaseClient;
