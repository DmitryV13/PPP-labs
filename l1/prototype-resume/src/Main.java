import java.util.*;

public class Main {
    public static void main(String[] args) {
        ResumeRegistry registry = new ResumeRegistry();

        // Создаем и регистрируем прототип резюме для интершипа
        JobResume internshipCV = new JobResume(
                "Voicu Dmitry",
                "1 year",
                "Bachelor's Degree",
                "Java, C++, Python",
                "English, Spanish",
                "AWS Certified, Scrum Master",
                "E-commerce Project, Software Engineering",
                "+1234567890",
                "dmitry@email.com",
                "123 Street, Chisinau, Moldova",
                "linkedin.com/in/dmitry",
                "github.com/dmitry");
        JobResume juniorEngineerCV = new JobResume(
                "Gheorghii Scalazub",
                "3 year",
                "Bachelor's Degree",
                "Java, C++, Python",
                "English, Spanish, Romanian, Russian",
                "AWS Certified, Java Certified",
                "Business Analytic, Software Engineering",
                "+1234567890",
                "gheorghii@email.com",
                "43 Street, Orhei, Moldova",
                "linkedin.com/in/gheorghii",
                "github.com/gheorghii");

        registry.addPrototype("internshipCV", internshipCV);
        registry.addPrototype("juniorCV", juniorEngineerCV);

        // Клонируем и обновляем резюме для junior позиции
        Resume updatedJuniorCV = registry.getPrototype("juniorCV");
        updatedJuniorCV.setExperience("4 years as a Software Developer");
        updatedJuniorCV.setEmail("gheorghiiNew@email.com");
        updatedJuniorCV.showInfo();

        // Клонируем и обновляем резюме для internship
        Resume updatedInternshiopCV = registry.getPrototype("internshipCV");
        updatedInternshiopCV.setPhoneNumber("+9876543210");
        updatedInternshiopCV.showInfo();
    }
}

// RESUME PROTOTYPE
interface Resume{
    Resume clone();
    void setName(String name);
    void setExperience(String experience);
    void setEducation(String education);
    void setSkills(String skills);
    void setLanguages(String languages);
    void setCertifications(String certifications);
    void setProjects(String projects);
    void setPhoneNumber(String phoneNumber);
    void setEmail(String email);
    void setAddress(String address);
    void setLinkedIn(String linkedIn);
    void setGitHub(String gitHub);
    void showInfo();
}

// CONCRETE RESUME PROTOTYPE
class JobResume implements Resume {
    private String name;
    private String experience;
    private String education;
    private String skills;
    private String languages;
    private String certifications;
    private String projects;
    private String phoneNumber;
    private String email;
    private String address;
    private String linkedIn;
    private String gitHub;

    public JobResume(String name, String experience, String education, String skills, String languages, String certifications,
                     String projects, String phoneNumber, String email, String address, String linkedIn, String gitHub) {
        this.name = name;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.languages = languages;
        this.certifications = certifications;
        this.projects = projects;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.linkedIn = linkedIn;
        this.gitHub = gitHub;
    }

    @Override
    public Resume clone() {
        return new JobResume(name, experience, education, skills, languages, certifications,
                projects, phoneNumber, email, address, linkedIn, gitHub);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setExperience(String experience) {
        this.experience = experience;
    }
    @Override
    public void setEducation(String education) {
        this.education = education;
    }
    @Override
    public void setSkills(String skills) {
        this.skills = skills;
    }
    @Override
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    @Override
    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
    @Override
    public void setProjects(String projects) {
        this.projects = projects;
    }
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    @Override
    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    @Override
    public void showInfo() {
        System.out.println("-----------------------------------------------------");
        System.out.println("------------------------MY CV------------------------");
        System.out.println("Name: " + name);
        System.out.println("Experience: " + experience);
        System.out.println("Education: " + education);
        System.out.println("Skills: " + skills);
        System.out.println("Languages: " + languages);
        System.out.println("Certifications: " + certifications);
        System.out.println("Projects: " + projects);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("LinkedIn: " + linkedIn);
        System.out.println("GitHub: " + gitHub);

    }
}

// RESUME MANAGER
class ResumeRegistry {
    private HashMap<String, Resume> prototypes = new HashMap<>();

    public void addPrototype(String key, Resume resume) {
        prototypes.put(key, resume);
    }

    public Resume getPrototype(String key) {
        return prototypes.get(key).clone();
    }
}
