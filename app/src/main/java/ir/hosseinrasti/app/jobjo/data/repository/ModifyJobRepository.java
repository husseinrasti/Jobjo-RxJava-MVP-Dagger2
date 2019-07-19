package ir.hosseinrasti.app.jobjo.data.repository;

import io.reactivex.Single;
import ir.hosseinrasti.app.jobjo.data.DataSource;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.data.local.SessionPref;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;

/**
 * Created by Hossein on 5/31/2018.
 */

public class ModifyJobRepository implements DataSource.ModifyJob {

    private ApiService apiService;
    private SessionPref pref;

    public ModifyJobRepository( ApiService apiService, SessionPref pref ) {
        this.apiService = apiService;
        this.pref = pref;
    }

    @Override
    public Single<JobModel> insertJobModel( JobModel jobModel ) {
        return apiService.insertJobModel(
                jobModel.getIdWorkGroup(),
                pref.getId(),
                jobModel.getNameJob(),
                pref.getPic(),
                jobModel.getPicJob(),
                jobModel.getNameWorkGroup(),
                pref.getFullname(),
                jobModel.getAddress(),
                jobModel.getTypeOfJob(),
                jobModel.getHoursOfWork(),
                jobModel.getBenefit(),
                jobModel.getTravelOfWork(),
                jobModel.getGender(),
                jobModel.getEducation(),
                jobModel.getLang(),
                jobModel.getAge(),
                jobModel.getJobDescription(),
                jobModel.getProfessionalKnowledge(),
                jobModel.getSoftware(),
                jobModel.getWorkExperience(),
                jobModel.getMilitaryService() );
    }

    @Override
    public Single<JobModel> updateJobModel( JobModel jobModel ) {
        return apiService.updateJobById( jobModel.getId(),
                jobModel.getNameJob(),
                jobModel.getIdWorkGroup(),
                pref.getId(),
                jobModel.getNameWorkGroup(),
                pref.getFullname(),
                jobModel.getAddress(),
                jobModel.getTypeOfJob(),
                jobModel.getHoursOfWork(),
                jobModel.getBenefit(),
                jobModel.getTravelOfWork(),
                jobModel.getGender(),
                jobModel.getEducation(),
                jobModel.getLang(),
                jobModel.getAge(),
                jobModel.getJobDescription(),
                jobModel.getProfessionalKnowledge(),
                jobModel.getSoftware(),
                jobModel.getWorkExperience(),
                jobModel.getMilitaryService(),
                jobModel.getPicJob() );
    }
}
