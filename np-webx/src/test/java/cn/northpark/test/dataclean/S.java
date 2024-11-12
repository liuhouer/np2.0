package cn.northpark.test.dataclean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce
 * @date 2024年07月18日 11:24:03
 */
public class S {
    public static void main(String[] args) {
        String origins = " <mapping wildcard=\"true\">\n" +
                "        <class-a>cn.crhms.gateway.model.aggr.MedicalCaseModel</class-a>\n" +
                "        <class-b>cn.crhms.gateway.model.pma.Case</class-b>\n" +
                "        <field><a>is_discharge</a><b>is_discharge</b></field>\n" +
                "        <field><a>hospital_id</a><b>hospital_id</b></field>\n" +
                "        <field><a>hospital_name</a><b>hospital_name</b></field>\n" +
                "        <field><a>hospital_level</a><b>hospital_level</b></field>\n" +
                "        <field><a>hospital_fee_id</a><b>hospital_fee_id</b></field>\n" +
                "        <field><a>hospital_fee_name</a><b>hospital_fee_name</b></field>\n" +
                "        <field><a>hospital_out_name</a><b>hospital_out_name</b></field>\n" +
                "        <field><a>area_id</a><b>area_id</b></field>\n" +
                "        <field><a>area_name</a><b>area_name</b></field>\n" +
                "        <field><a>fund_type_id</a><b>fund_type_id</b></field>\n" +
                "        <field><a>fund_type_name</a><b>fund_type_name</b></field>\n" +
                "        <field><a>benefit_type_id</a><b>benefit_type_id</b></field>\n" +
                "        <field><a>benefit_type_name</a><b>benefit_type_name</b></field>\n" +
                "        <field><a>benefit_group_id</a><b>benefit_group_id</b></field>\n" +
                "        <field><a>benefit_group_name</a><b>benefit_group_name</b></field>\n" +
                "        <field><a>patient_id</a><b>patient_id</b></field>\n" +
                "        <field><a>patient_idno</a><b>patient_idno</b></field>\n" +
                "        <field><a>patient_name</a><b>patient_name</b></field>\n" +
                "        <field><a>patient_gender</a><b>patient_gender</b></field>\n" +
                "        <field><a>patient_birthday</a><b date-format=\"yyyy-MM-dd\">patient_birthday</b></field>\n" +
                "        <field><a>patien_identity</a><b>patien_identity</b></field>\n" +
                "        <field><a>blood_type_s</a><b>blood_type_s</b></field>\n" +
                "        <field><a>blood_type_e</a><b>blood_type_e</b></field>\n" +
                "        <field><a>chief_complaint</a><b>chief_complaint</b></field>\n" +
                "        <field><a>medical_history</a><b>medical_history</b></field>\n" +
                "        <field><a>height</a><b>height</b></field>\n" +
                "        <field><a>weight</a><b>weight</b></field>\n" +
                "        <field><a>marriage</a><b>marriage</b></field>\n" +
                "        <field><a>bear_pregnancy</a><b>bear_pregnancy</b></field>\n" +
                "        <field><a>bear_yie</a><b>bear_yie</b></field>\n" +
                "        <field><a>surgery_history</a><b>surgery_history</b></field>\n" +
                "        <field><a>blood_trans_history</a><b>blood_trans_history</b></field>\n" +
                "        <field><a>newborn_date</a><b>newborn_date</b></field>\n" +
                "        <field><a>newborn_weight</a><b>newborn_weight</b></field>\n" +
                "        <field><a>newborn_current_weight</a><b>newborn_current_weight</b></field>\n" +
                "        <field><a>is_drug_allergy</a><b>is_drug_allergy</b></field>\n" +
                "        <field><a>allergy_drug_code</a><b>allergy_drug_code</b></field>\n" +
                "        <field><a>allergy_drug_name</a><b>allergy_drug_name</b></field>\n" +
                "        <field><a>is_pregnant</a><b>patient_pregnant</b></field>\n" +
                "        <field><a>is_lactating</a><b>patient_lactating</b></field>\n" +
                "        <field><a>first_date</a><b>first_date</b></field>\n" +
                "        <field><a>admission_no</a><b>admission_no</b></field>\n" +
                "        <field><a>medical_record_id</a><b>medical_record_id</b></field>\n" +
                "        <field><a>hospitalized_no</a><b>hospitalized_no</b></field>\n" +
                "        <field><a>in_bed_no</a><b>in_bed_no</b></field>\n" +
                "        <field><a>out_bed_no</a><b>out_bed_no</b></field>\n" +
                "        <field><a>hospital_area_id</a><b>hospital_area_id</b></field>\n" +
                "        <field><a>hospital_area_name</a><b>hospital_area_name</b></field>\n" +
                "        <field><a>units_code</a><b>units_code</b></field>\n" +
                "        <field><a>units_name</a><b>units_name</b></field>\n" +
                "        <field><a>in_dept_code</a><b>imdept_code</b></field>\n" +
                "        <field><a>in_dept_name</a><b>imdept_name</b></field>\n" +
                "        <field><a>trans_dept_code</a><b>transferDept_id</b></field>\n" +
                "        <field><a>trans_dept_name</a><b>transferDept_name</b></field>\n" +
                "        <field><a>dept_code</a><b>dept_code</b></field>\n" +
                "        <field><a>dept_name</a><b>dept_name</b></field>\n" +
                "        <field><a>out_zone_code</a><b>out_zone_code</b></field>\n" +
                "        <field><a>out_zone_name</a><b>out_zone_name</b></field>\n" +
                "        <field><a>doctor_group_code</a><b>doctor_group_code</b></field>\n" +
                "        <field><a>doctor_group_name</a><b>doctor_group_name</b></field>\n" +
                "        <field><a>doctor_code</a><b>doctor_code</b></field>\n" +
                "        <field><a>doctor_name</a><b>doctor_name</b></field>\n" +
                "        <field><a>claim_type_id</a><b>claim_type_id</b></field>\n" +
                "        <field><a>claim_type_name</a><b>claim_type_name</b></field>\n" +
                "        <field><a>unusual_flag</a><b>unusual_flag</b></field>\n" +
                "        <field><a>in_patient_trans_flag</a><b>in_patient_transfer_flag</b></field>\n" +
                "        <field><a>hospital_time</a><b>hospital_time</b></field>\n" +
                "        <field><a>leave_hospital_type</a><b>leave_hospital_type</b></field>\n" +
                "        <field><a>is_upload_date</a><b>is_upload_date</b></field>\n" +
                "        <field><a>admission_disease_id</a><b>admission_disease_id</b></field>\n" +
                "        <field><a>admission_disease_name</a><b>admission_disease_name</b></field>\n" +
                "        <field><a>admission_disease_id_gl</a><b>admission_disease_id_gl</b></field>\n" +
                "        <field><a>admission_disease_name_gl</a><b>admission_disease_name_gl</b></field>\n" +
                "        <field><a>admission_date</a><b>admission_date</b></field>\n" +
                "        <field><a>discharge_disease_id</a><b>discharge_disease_id</b></field>\n" +
                "        <field><a>discharge_disease_name</a><b>discharge_disease_name</b></field>\n" +
                "        <field><a>discharge_disease_id_gl</a><b>discharge_disease_id_gl</b></field>\n" +
                "        <field><a>discharge_disease_name_gl</a><b>discharge_disease_name_gl</b></field>\n" +
                "        <field><a>discharge_disease_situation</a><b>discharge_disease_situation</b></field>\n" +
                "        <field><a>diagnosis_situation</a><b>diagnosis_situation</b></field>\n" +
                "        <field><a>discharge_reason</a><b>discharge_reason</b></field>\n" +
                "        <field><a>discharge_date</a><b>discharge_date</b></field>\n" +
                "        <field><a>tsblbs</a><b>tsblbs</b></field>\n" +
                "        <field><a>diagnose_position1</a><b>diagnose_position1</b></field>\n" +
                "        <field><a>diagnose_position2</a><b>diagnose_position2</b></field>\n" +
                "        <field><a>is_pathology_exam</a><b>is_pathological_examination</b></field>\n" +
                "        <field><a>pathology_code</a><b>pathology_code</b></field>\n" +
                "        <field><a>is_hospital_infected</a><b>is_hospital_infected</b></field>\n" +
                "        <field><a>hospital_infected_code</a><b>hospital_infected_code</b></field>\n" +
                "        <field><a>is_disease_type</a><b>disease_type</b></field>\n" +
                "        <field><a>special_disease_code</a><b>special_disease_code</b></field>\n" +
                "        <field><a>diagnosis_code1</a><b>diagnosis_code1</b></field>\n" +
                "        <field><a>diagnosis_name1</a><b>diagnosis_name1</b></field>\n" +
                "        <field><a>diagnosis_code2</a><b>diagnosis_code2</b></field>\n" +
                "        <field><a>diagnosis_name2</a><b>diagnosis_name2</b></field>\n" +
                "        <field><a>diagnosis_code3</a><b>diagnosis_code3</b></field>\n" +
                "        <field><a>diagnosis_name3</a><b>diagnosis_name3</b></field>\n" +
                "        <field><a>diagnosis_code4</a><b>diagnosis_code4</b></field>\n" +
                "        <field><a>diagnosis_name4</a><b>diagnosis_name4</b></field>\n" +
                "        <field><a>diagnosis_code5</a><b>diagnosis_code5</b></field>\n" +
                "        <field><a>diagnosis_name5</a><b>diagnosis_name5</b></field>\n" +
                "        <field><a>diagnosis_code6</a><b>diagnosis_code6</b></field>\n" +
                "        <field><a>diagnosis_name6</a><b>diagnosis_name6</b></field>\n" +
                "        <field><a>diagnosis_code7</a><b>diagnosis_code7</b></field>\n" +
                "        <field><a>diagnosis_name7</a><b>diagnosis_name7</b></field>\n" +
                "        <field><a>diagnosis_code8</a><b>diagnosis_code8</b></field>\n" +
                "        <field><a>diagnosis_name8</a><b>diagnosis_name8</b></field>\n" +
                "        <field><a>diagnosis_code9</a><b>diagnosis_code9</b></field>\n" +
                "        <field><a>diagnosis_name9</a><b>diagnosis_name9</b></field>\n" +
                "        <field><a>diagnosis_code10</a><b>diagnosis_code10</b></field>\n" +
                "        <field><a>diagnosis_name10</a><b>diagnosis_name10</b></field>\n" +
                "        <field><a>diagnosis_code11</a><b>diagnosis_code11</b></field>\n" +
                "        <field><a>diagnosis_name11</a><b>diagnosis_name11</b></field>\n" +
                "        <field><a>diagnosis_code12</a><b>diagnosis_code12</b></field>\n" +
                "        <field><a>diagnosis_name12</a><b>diagnosis_name12</b></field>\n" +
                "        <field><a>diagnosis_code13</a><b>diagnosis_code13</b></field>\n" +
                "        <field><a>diagnosis_name13</a><b>diagnosis_name13</b></field>\n" +
                "        <field><a>diagnosis_code14</a><b>diagnosis_code14</b></field>\n" +
                "        <field><a>diagnosis_name14</a><b>diagnosis_name14</b></field>\n" +
                "        <field><a>diagnosis_code15</a><b>diagnosis_code15</b></field>\n" +
                "        <field><a>diagnosis_name15</a><b>diagnosis_name15</b></field>\n" +
                "        <field><a>diagnosis_code16</a><b>diagnosis_code16</b></field>\n" +
                "        <field><a>diagnosis_name16</a><b>diagnosis_name16</b></field>\n" +
                "        <field><a>diagnosis_code17</a><b>diagnosis_code17</b></field>\n" +
                "        <field><a>diagnosis_name17</a><b>diagnosis_name17</b></field>\n" +
                "        <field><a>diagnosis_code18</a><b>diagnosis_code18</b></field>\n" +
                "        <field><a>diagnosis_name18</a><b>diagnosis_name18</b></field>\n" +
                "        <field><a>diagnosis_code19</a><b>diagnosis_code19</b></field>\n" +
                "        <field><a>diagnosis_name19</a><b>diagnosis_name19</b></field>\n" +
                "        <field><a>diagnosis_code20</a><b>diagnosis_code20</b></field>\n" +
                "        <field><a>diagnosis_name20</a><b>diagnosis_name20</b></field>\n" +
                "        <field><a>diagnosis_code21</a><b>diagnosis_code21</b></field>\n" +
                "        <field><a>diagnosis_name21</a><b>diagnosis_name21</b></field>\n" +
                "        <field><a>diagnosis_code22</a><b>diagnosis_code22</b></field>\n" +
                "        <field><a>diagnosis_name22</a><b>diagnosis_name22</b></field>\n" +
                "        <field><a>diagnosis_code23</a><b>diagnosis_code23</b></field>\n" +
                "        <field><a>diagnosis_name23</a><b>diagnosis_name23</b></field>\n" +
                "        <field><a>diagnosis_code24</a><b>diagnosis_code24</b></field>\n" +
                "        <field><a>diagnosis_name24</a><b>diagnosis_name24</b></field>\n" +
                "        <field><a>diagnosis_code25</a><b>diagnosis_code25</b></field>\n" +
                "        <field><a>diagnosis_name25</a><b>diagnosis_name25</b></field>\n" +
                "        <field><a>diagnosis_code26</a><b>diagnosis_code26</b></field>\n" +
                "        <field><a>diagnosis_name26</a><b>diagnosis_name26</b></field>\n" +
                "        <field><a>diagnosis_code27</a><b>diagnosis_code27</b></field>\n" +
                "        <field><a>diagnosis_name27</a><b>diagnosis_name27</b></field>\n" +
                "        <field><a>diagnosis_code28</a><b>diagnosis_code28</b></field>\n" +
                "        <field><a>diagnosis_name28</a><b>diagnosis_name28</b></field>\n" +
                "        <field><a>diagnosis_code29</a><b>diagnosis_code29</b></field>\n" +
                "        <field><a>diagnosis_name29</a><b>diagnosis_name29</b></field>\n" +
                "        <field><a>diagnosis_code30</a><b>diagnosis_code30</b></field>\n" +
                "        <field><a>diagnosis_name30</a><b>diagnosis_name30</b></field>\n" +
                "        <field><a>diagnosis_code31</a><b>diagnosis_code31</b></field>\n" +
                "        <field><a>diagnosis_name31</a><b>diagnosis_name31</b></field>\n" +
                "        <field><a>diagnosis_code32</a><b>diagnosis_code32</b></field>\n" +
                "        <field><a>diagnosis_name32</a><b>diagnosis_name32</b></field>\n" +
                "        <field><a>diagnosis_code1_gl</a><b>diagnosis_code1_gl</b></field>\n" +
                "        <field><a>diagnosis_name1_gl</a><b>diagnosis_name1_gl</b></field>\n" +
                "        <field><a>diagnosis_code2_gl</a><b>diagnosis_code2_gl</b></field>\n" +
                "        <field><a>diagnosis_name2_gl</a><b>diagnosis_name2_gl</b></field>\n" +
                "        <field><a>diagnosis_code3_gl</a><b>diagnosis_code3_gl</b></field>\n" +
                "        <field><a>diagnosis_name3_gl</a><b>diagnosis_name3_gl</b></field>\n" +
                "        <field><a>diagnosis_code4_gl</a><b>diagnosis_code4_gl</b></field>\n" +
                "        <field><a>diagnosis_name4_gl</a><b>diagnosis_name4_gl</b></field>\n" +
                "        <field><a>diagnosis_code5_gl</a><b>diagnosis_code5_gl</b></field>\n" +
                "        <field><a>diagnosis_name5_gl</a><b>diagnosis_name5_gl</b></field>\n" +
                "        <field><a>diagnosis_code6_gl</a><b>diagnosis_code6_gl</b></field>\n" +
                "        <field><a>diagnosis_name6_gl</a><b>diagnosis_name6_gl</b></field>\n" +
                "        <field><a>diagnosis_code7_gl</a><b>diagnosis_code7_gl</b></field>\n" +
                "        <field><a>diagnosis_name7_gl</a><b>diagnosis_name7_gl</b></field>\n" +
                "        <field><a>diagnosis_code8_gl</a><b>diagnosis_code8_gl</b></field>\n" +
                "        <field><a>diagnosis_name8_gl</a><b>diagnosis_name8_gl</b></field>\n" +
                "        <field><a>diagnosis_code9_gl</a><b>diagnosis_code9_gl</b></field>\n" +
                "        <field><a>diagnosis_name9_gl</a><b>diagnosis_name9_gl</b></field>\n" +
                "        <field><a>diagnosis_code10_gl</a><b>diagnosis_code10_gl</b></field>\n" +
                "        <field><a>diagnosis_name10_gl</a><b>diagnosis_name10_gl</b></field>\n" +
                "        <field><a>diagnosis_code11_gl</a><b>diagnosis_code11_gl</b></field>\n" +
                "        <field><a>diagnosis_name11_gl</a><b>diagnosis_name11_gl</b></field>\n" +
                "        <field><a>diagnosis_code12_gl</a><b>diagnosis_code12_gl</b></field>\n" +
                "        <field><a>diagnosis_name12_gl</a><b>diagnosis_name12_gl</b></field>\n" +
                "        <field><a>diagnosis_code13_gl</a><b>diagnosis_code13_gl</b></field>\n" +
                "        <field><a>diagnosis_name13_gl</a><b>diagnosis_name13_gl</b></field>\n" +
                "        <field><a>diagnosis_code14_gl</a><b>diagnosis_code14_gl</b></field>\n" +
                "        <field><a>diagnosis_name14_gl</a><b>diagnosis_name14_gl</b></field>\n" +
                "        <field><a>diagnosis_code15_gl</a><b>diagnosis_code15_gl</b></field>\n" +
                "        <field><a>diagnosis_name15_gl</a><b>diagnosis_name15_gl</b></field>\n" +
                "        <field><a>diagnosis_code16_gl</a><b>diagnosis_code16_gl</b></field>\n" +
                "        <field><a>diagnosis_name16_gl</a><b>diagnosis_name16_gl</b></field>\n" +
                "        <field><a>diagnosis_code17_gl</a><b>diagnosis_code17_gl</b></field>\n" +
                "        <field><a>diagnosis_name17_gl</a><b>diagnosis_name17_gl</b></field>\n" +
                "        <field><a>diagnosis_code18_gl</a><b>diagnosis_code18_gl</b></field>\n" +
                "        <field><a>diagnosis_name18_gl</a><b>diagnosis_name18_gl</b></field>\n" +
                "        <field><a>diagnosis_code19_gl</a><b>diagnosis_code19_gl</b></field>\n" +
                "        <field><a>diagnosis_name19_gl</a><b>diagnosis_name19_gl</b></field>\n" +
                "        <field><a>diagnosis_code20_gl</a><b>diagnosis_code20_gl</b></field>\n" +
                "        <field><a>diagnosis_name20_gl</a><b>diagnosis_name20_gl</b></field>\n" +
                "        <field><a>diagnosis_code21_gl</a><b>diagnosis_code21_gl</b></field>\n" +
                "        <field><a>diagnosis_name21_gl</a><b>diagnosis_name21_gl</b></field>\n" +
                "        <field><a>diagnosis_code22_gl</a><b>diagnosis_code22_gl</b></field>\n" +
                "        <field><a>diagnosis_name22_gl</a><b>diagnosis_name22_gl</b></field>\n" +
                "        <field><a>diagnosis_code23_gl</a><b>diagnosis_code23_gl</b></field>\n" +
                "        <field><a>diagnosis_name23_gl</a><b>diagnosis_name23_gl</b></field>\n" +
                "        <field><a>diagnosis_code24_gl</a><b>diagnosis_code24_gl</b></field>\n" +
                "        <field><a>diagnosis_name24_gl</a><b>diagnosis_name24_gl</b></field>\n" +
                "        <field><a>diagnosis_code25_gl</a><b>diagnosis_code25_gl</b></field>\n" +
                "        <field><a>diagnosis_name25_gl</a><b>diagnosis_name25_gl</b></field>\n" +
                "        <field><a>diagnosis_code26_gl</a><b>diagnosis_code26_gl</b></field>\n" +
                "        <field><a>diagnosis_name26_gl</a><b>diagnosis_name26_gl</b></field>\n" +
                "        <field><a>diagnosis_code27_gl</a><b>diagnosis_code27_gl</b></field>\n" +
                "        <field><a>diagnosis_name27_gl</a><b>diagnosis_name27_gl</b></field>\n" +
                "        <field><a>diagnosis_code28_gl</a><b>diagnosis_code28_gl</b></field>\n" +
                "        <field><a>diagnosis_name28_gl</a><b>diagnosis_name28_gl</b></field>\n" +
                "        <field><a>diagnosis_code29_gl</a><b>diagnosis_code29_gl</b></field>\n" +
                "        <field><a>diagnosis_name29_gl</a><b>diagnosis_name29_gl</b></field>\n" +
                "        <field><a>diagnosis_code30_gl</a><b>diagnosis_code30_gl</b></field>\n" +
                "        <field><a>diagnosis_name30_gl</a><b>diagnosis_name30_gl</b></field>\n" +
                "        <field><a>diagnosis_code31_gl</a><b>diagnosis_code31_gl</b></field>\n" +
                "        <field><a>diagnosis_name31_gl</a><b>diagnosis_name31_gl</b></field>\n" +
                "        <field><a>diagnosis_code32_gl</a><b>diagnosis_code32_gl</b></field>\n" +
                "        <field><a>diagnosis_name32_gl</a><b>diagnosis_name32_gl</b></field>\n" +
                "<!--        <field><a>discharge_outcome</a><b>discharge_outcome</b></field>-->\n" +
                "        <field><a>billdate</a><b>billdate</b></field>\n" +
                "        <field><a>bill_no</a><b>bill_no</b></field>\n" +
                "        <field><a>bmi_code</a><b>bmi_code</b></field>\n" +
                "        <field><a>bmi_name</a><b>bmi_name</b></field>\n" +
                "        <field><a>total_amount</a><b>total_amount</b></field>\n" +
                "        <field><a>total_amount_all</a><b>total_amount_all</b></field>\n" +
                "        <field><a>tczf</a><b>tczf</b></field>\n" +
                "        <field><a>tradeno</a><b>tradeno</b></field>\n" +
                "        <field><a>specified_diseases</a><b>specified_diseases</b></field>\n" +
                "        <field><a>wiped_money</a><b>wiped_money</b></field>\n" +
                "        <field><a>cash_pay</a><b>cash_pay</b></field>\n" +
                "        <field><a>longterm</a><b>longterm</b></field>\n" +
                "        <field><a>settle_status</a><b>settle_status</b></field>\n" +
                "        <field><a>cr_code</a><b>cr_code</b></field>\n" +
                "        <field><a>cr_name</a><b>cr_name</b></field>\n" +
                "        <field><a>clr_way</a><b>clr_way</b></field>\n" +
                "        <field><a>hif_pay</a><b>hif_pay</b></field>\n" +
                "        <field><a>hospital_num</a><b>hospital_num</b></field>\n" +
                "        <field><a>paytype</a><b>pay_type</b></field>\n" +
                "        <field><a>hifmi_pay</a><b>hifmi_pay</b></field>\n" +
                "        <field><a>hifob_pay</a><b>hifob_pay</b></field>\n" +
                "        <field><a>doctor_id</a><b>doctor_id</b></field>\n" +
                "<!--        <field><a>oper_id</a><b>oper_id</b></field>-->\n" +
                "<!--        <field><a>oper_date</a><b>oper_date</b></field>-->\n" +
                "<!--        <field><a>is_upload</a><b>is_upload</b></field>-->\n" +
                "<!--        <field><a>is_valid</a><b>is_valid</b></field>-->\n" +
                "<!--        <field><a>operator</a><b>operator</b></field>-->\n" +
                "<!--        <field><a>operating_date</a><b>operating_date</b></field>-->\n" +
                "    </mapping>\n" +
                "    ";


        List<String> list = new ArrayList<>();
        list.add("is_grouped");
        list.add("admission_disease_id_gl");
        list.add("admission_disease_name_gl");
        list.add("readmission_day");
        list.add("discharge_reason");
        list.add("is_disease_type");
        list.add("special_disease_code");
        list.add("specified_diseases");
        list.add("main_opr_code");
        list.add("main_opr_name");
        list.add("aux_oper_code");
        list.add("aux_oper_name");
        list.add("is_exam_hospitalization");
        list.add("is_dosage_hospitalization");
        list.add("is_resolve_hospitalization");
        list.add("is_bed_hospitalization");
        list.add("fjzy_flag");
        list.add("tjzy_flag");
        list.add("pyzy_flag");
        list.add("wzlzy_flag");
        list.add("cczy_flag");
        list.add("gczy_flag");
        list.add("drg_code");
        list.add("adrg_code");
        list.add("mdc_code");
        list.add("isequals_drg");
        list.add("clr_name");

        for (String s : list) {
//            System.err.println(s +":"+ (origins.contains(s)?"是":"否"));
            System.err.println( (origins.contains(s)?"是":"否"));
        }
    }



}
